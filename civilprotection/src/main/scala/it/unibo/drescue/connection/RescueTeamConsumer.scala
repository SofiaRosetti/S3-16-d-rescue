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
    println("[AlertConsumer] " + message)

    val coordinator : Coordinator = CoordinatorImpl.getInstance()
    var myCondition : CoordinatorCondition = null
    var myCs : String = null
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
          println("[REQUEST] From: " + reqFrom + " To: " + reqTo + " Timestamp: " + reqTimestamp + " Cs: " + reqCs)
          myCondition = coordinator.getCondition
          myCs = coordinator.getCsName
          myTimestamp = coordinator.getReqTimestamp

          if (((CoordinatorCondition.HELD == myCondition) && reqCs == myCs) ||
            ((CoordinatorCondition.WANTED ==  myCondition) && reqCs == myCs && reqTimestamp.after(myTimestamp)))
            coordinator.addBlockedCP(reqFrom)
          else {

            //TODO se la rescue team è occupata, ed è stata occupata da altre CP  allora settare lo stato del RT = AVAILABLE
            coordinator.sendReplayMessageTo(reqCoordinationMessage.getRescueTeamID, reqFrom)
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
          System.out.println("[REPLAY] From: " + replayFrom + " To: " + replayTo + " Timestamp: " + replayTimestamp + " Cs: " + replayCs + " RT Condition " + replayRTCondition)
          if ((myCondition eq CoordinatorCondition.WANTED) && myCs == replayCs && replayTo == mainControllerImpl.model.cpID) {
            //TODO Check RescueTeam state (if state = Occupied then update coordination state)
            if (replayRTCondition eq RescueTeamCondition.AVAILABLE) coordinator.updatePendingCivilProtectionReplayStructure(replayFrom)
            else coordinator.setCondition(CoordinatorCondition.DETACHED)
          }
        }

      case MessageType.CONFIGURATION_MESSAGE =>
        val cpConfigurationMessage = GsonUtils.fromGson(message, classOf[CPConfigurationMessage])
        println("[Configuration Message] RescueTeam name: " + cpConfigurationMessage.getRescueTeamCollection.get(0).getName + " From: " + cpConfigurationMessage.getFrom + " To: " + cpConfigurationMessage.getTo)

      case MessageType.REPLY_RESCUE_TEAM_CONDITION =>
        val replyRescueTeamConditionMessage = GsonUtils.fromGson(message, classOf[ReplyRescueTeamConditionMessage])
        println("[Replay RT condition Message] RescueTeam name: " + replyRescueTeamConditionMessage.getRescueTeamID + " Condition: " + replyRescueTeamConditionMessage.getRescueTeamCondition + " From: " + replyRescueTeamConditionMessage.getFrom + " To: " + replyRescueTeamConditionMessage.getTo)
        if (!(replyRescueTeamConditionMessage.getFrom == mainControllerImpl.model.cpID)) {
          //TODO update rescue team condition into model
        }

      case MessageType.REQ_RESCUE_TEAM_CONDITION =>
        //TODO check from
        val reqRescueTeamConditionMessage = GsonUtils.fromGson(message, classOf[ReqRescueTeamConditionMessage])
        println("[Req RT condition Message] RescueTeam name: " + reqRescueTeamConditionMessage.getRescueTeamID + " From: " + reqRescueTeamConditionMessage.getFrom + " To: " + reqRescueTeamConditionMessage.getTo)

        println("Enrolled team info size: " + mainControllerImpl.model.enrolledTeamInfoList.size())

        mainControllerImpl.model.enrolledTeamInfoList forEach((enrolledTeam: EnrolledTeamInfo) => {
          val rescueTeamID = enrolledTeam.teamID.value
          println("rescueTeamID : " + rescueTeamID)
          if (rescueTeamID == reqRescueTeamConditionMessage.getRescueTeamID) {

            val rtAvailability : String = enrolledTeam.availability.value
            val cpID : String = enrolledTeam.cpID.value

            println("Availability: "+ rtAvailability)
            println("cpID: " + cpID)

            if (rtAvailability == "false" && cpID == mainControllerImpl.model.cpID){
              //TODO send a reply_rescue_team_condition condition OCCUPIED
            }
            else {
              //TODO send a reply_rescue_team_condition condition AVAILABLE
              println("Send replay message AVAILABLE")

              val reply : Message = new ReplyRescueTeamConditionMessageBuilderImpl()
                .setRescueTeamID(rescueTeamID)
                .setRescueTeamCondition(RescueTeamCondition.AVAILABLE)
                .setFrom(mainControllerImpl.model.cpID)
                .build()

                rabbitMQ.sendMessage(mainControllerImpl.ExchangeName, rescueTeamID, null, reply)
            }
          }

        })

      case _ => //do nothing

    }

  }

}