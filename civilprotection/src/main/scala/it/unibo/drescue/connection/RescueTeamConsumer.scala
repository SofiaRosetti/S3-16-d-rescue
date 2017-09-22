package it.unibo.drescue.connection

import java.sql.Timestamp

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.ReplyRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.controller.MainControllerImpl
import it.unibo.drescue.localModel.EnrolledTeamInfo
import it.unibo.drescue.utils.{Coordinator, CoordinatorCondition, CoordinatorImpl, RescueTeamCondition}

case class RescueTeamConsumer(private val rabbitMQ: RabbitMQ,
                              private val mainControllerImpl: MainControllerImpl)
  extends DefaultConsumer(rabbitMQ.getChannel) {

  override def handleDelivery(consumerTag: String,
                              envelope: Envelope,
                              properties: AMQP.BasicProperties,
                              body: Array[Byte]): Unit = {

    super.handleDelivery(consumerTag, envelope, properties, body)

    val message = new String(body, "UTF-8")
    println("[RescueTeamConsumer] " + message)

    val coordinator: Coordinator = CoordinatorImpl.getInstance()
    coordinator.setConnection(rabbitMQ)
    coordinator.setExchange(mainControllerImpl.ExchangeName)
    coordinator.setMyID(mainControllerImpl.model.cpID)
    var myCondition: CoordinatorCondition = null
    var myCs: String = null
    var myTimestamp: Timestamp = null

    val messageName: MessageType = MessageUtils.getMessageNameByJson(message)

    messageName match {

      case MessageType.REQ_COORDINATION_MESSAGE =>
        val reqCoordinationMessage = GsonUtils.fromGson(message, classOf[ReqCoordinationMessage])
        val reqTimestamp = reqCoordinationMessage.getTimestamp
        val reqFrom = reqCoordinationMessage.getFrom
        val reqTo = reqCoordinationMessage.getTo
        val reqCs = reqCoordinationMessage.getRescueTeamID

        if (!(reqFrom == mainControllerImpl.model.cpID)) {
          println("[COORDINATION REQUEST] From: " + reqFrom + " To: " + reqTo + " Timestamp: " + reqTimestamp + " Cs: " + reqCs)
          myCondition = coordinator.getCondition
          myCs = coordinator.getCsName
          myTimestamp = coordinator.getReqTimestamp

          if (((CoordinatorCondition.HELD == myCondition) && reqCs == myCs) ||
            ((CoordinatorCondition.WANTED == myCondition) && reqCs == myCs && reqTimestamp.after(myTimestamp)))
            coordinator.addBlockedCP(reqFrom)
          else {
            var replyRtCondition: RescueTeamCondition = RescueTeamCondition.AVAILABLE
            mainControllerImpl.model.enrolledTeamInfoList forEach ((enrolledTeam: EnrolledTeamInfo) => {
              val rtAvailability: String = enrolledTeam.availability.value
              val cpID: String = enrolledTeam.cpID.value
              val rtID: String = enrolledTeam.teamID.value
              if (rtAvailability == "false" && cpID == mainControllerImpl.model.cpID && reqCs == rtID) {
                replyRtCondition = RescueTeamCondition.OCCUPIED
              }
            })
            coordinator.sendReplayMessageTo(reqCs, reqFrom, replyRtCondition)
          }
        }

      case MessageType.REPLAY_COORDINATION_MESSAGE =>
        val replayMessage = GsonUtils.fromGson(message, classOf[ReplayCoordinationMessage])
        val replayTimestamp = replayMessage.getTimestamp
        val replayFrom = replayMessage.getFrom
        val replayTo = replayMessage.getTo
        val replayCs = replayMessage.getRescueTeamID
        val replayRTCondition = replayMessage.getRTCondition
        myCondition = coordinator.getCondition
        myCs = coordinator.getCsName
        if (!(replayFrom == mainControllerImpl.model.cpID)) {
          System.out.println("[COORDINATION REPLAY] From: " + replayFrom + " To: " + replayTo + " Timestamp: " + replayTimestamp + " Cs: " + replayCs + " RT Condition " + replayRTCondition)
          if ((myCondition == CoordinatorCondition.WANTED) && myCs == replayCs && replayTo == mainControllerImpl.model.cpID) {
            //Check RescueTeam state (if state = Occupied then set coordinator conditon = DETACHED)
            if (replayRTCondition == RescueTeamCondition.AVAILABLE)
              coordinator.updatePendingCivilProtectionReplayStructure(replayFrom)
            else
              coordinator.setCondition(CoordinatorCondition.DETACHED)
          }
        }

      case MessageType.CONFIGURATION_MESSAGE =>
        val cpConfigurationMessage = GsonUtils.fromGson(message, classOf[CPConfigurationMessage])
        println("[Configuration Message] RescueTeam name: " + cpConfigurationMessage.getRescueTeamCollection.get(0).getName
          + " From: " + cpConfigurationMessage.getFrom
          + " To: " + cpConfigurationMessage.getTo)

      case MessageType.REPLY_RESCUE_TEAM_CONDITION =>
        val replyRescueTeamConditionMessage = GsonUtils.fromGson(message, classOf[ReplyRescueTeamConditionMessage])
        println("[Replay RT condition Message] RescueTeam name: " + replyRescueTeamConditionMessage.getRescueTeamID
          + " Condition: " + replyRescueTeamConditionMessage.getRescueTeamCondition
          + " From: " + replyRescueTeamConditionMessage.getFrom
          + " To: " + replyRescueTeamConditionMessage.getTo)

        if (replyRescueTeamConditionMessage.getFrom != mainControllerImpl.model.cpID) {

          val rescueTeamCondition = replyRescueTeamConditionMessage.getRescueTeamCondition
          val rescueTeamID = replyRescueTeamConditionMessage.getRescueTeamID
          val senderCP = replyRescueTeamConditionMessage.getFrom

          var indexToChange: Int = -1
          mainControllerImpl.model.enrolledTeamInfoList forEach ((enrolledTeamInfo: EnrolledTeamInfo) => {
            val enrolledTeamID = enrolledTeamInfo.teamID.value
            if (rescueTeamID == enrolledTeamID) {
              indexToChange = mainControllerImpl.model.enrolledTeamInfoList.indexOf(enrolledTeamInfo)
            }
          })

          val list = mainControllerImpl.model.enrolledTeamInfoList

          rescueTeamCondition match {

            case RescueTeamCondition.OCCUPIED =>

              if (indexToChange != -1) {
                val enrolledTeamInfo = list.get(indexToChange)
                list.set(indexToChange, new EnrolledTeamInfo(
                  enrolledTeamInfo.teamID.value,
                  enrolledTeamInfo.teamName.value,
                  enrolledTeamInfo.phoneNumber.value,
                  false, //occupied
                  replyRescueTeamConditionMessage.getFrom, //cpID
                  enrolledTeamInfo.alertID.value)
                )
                mainControllerImpl.model.enrolledTeamInfoList = list
              }

            case RescueTeamCondition.AVAILABLE =>

              if (indexToChange != -1) {
                val enrolledTeamInfo = list.get(indexToChange)
                if (enrolledTeamInfo.cpID.value == senderCP) {
                  list.set(indexToChange, new EnrolledTeamInfo(
                    enrolledTeamInfo.teamID.value,
                    enrolledTeamInfo.teamName.value,
                    enrolledTeamInfo.phoneNumber.value,
                    true, //available
                    "", //cpID
                    "")
                  )
                  mainControllerImpl.model.enrolledTeamInfoList = list
                }
              }

          }
        }


      case MessageType.REQ_RESCUE_TEAM_CONDITION =>

        val reqRescueTeamConditionMessage = GsonUtils.fromGson(message, classOf[ReqRescueTeamConditionMessage])
        println("[Req RT condition Message] RescueTeam name: " + reqRescueTeamConditionMessage.getRescueTeamID
          + " From: " + reqRescueTeamConditionMessage.getFrom
          + " To: " + reqRescueTeamConditionMessage.getTo)

        if (reqRescueTeamConditionMessage.getFrom != mainControllerImpl.model.cpID) {
          mainControllerImpl.model.enrolledTeamInfoList forEach ((enrolledTeam: EnrolledTeamInfo) => {

            val rescueTeamID = enrolledTeam.teamID.value
            if (rescueTeamID == reqRescueTeamConditionMessage.getRescueTeamID) {

              val rtAvailability: String = enrolledTeam.availability.value
              val cpID: String = enrolledTeam.cpID.value

              var reply: Message = null
              if (rtAvailability == "false" && cpID == mainControllerImpl.model.cpID) {
                reply = new ReplyRescueTeamConditionMessageBuilderImpl()
                  .setRescueTeamID(rescueTeamID)
                  .setRescueTeamCondition(RescueTeamCondition.OCCUPIED)
                  .setFrom(mainControllerImpl.model.cpID)
                  .build()
              } else {
                reply = new ReplyRescueTeamConditionMessageBuilderImpl()
                  .setRescueTeamID(rescueTeamID)
                  .setRescueTeamCondition(RescueTeamCondition.AVAILABLE)
                  .setFrom(mainControllerImpl.model.cpID)
                  .build()
              }

              rabbitMQ.sendMessage(mainControllerImpl.ExchangeName, rescueTeamID, null, reply)

            }

          })
        }


      case _ => //do nothing

    }

  }

}