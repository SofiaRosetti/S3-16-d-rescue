package it.unibo.drescue.connection

import java.sql.Timestamp

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.ReplyRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.controller.MainControllerImpl
import it.unibo.drescue.localModel.EnrolledTeamInfo
import it.unibo.drescue.utils.{Coordinator, CoordinatorCondition, CoordinatorImpl, RescueTeamCondition}
import org.slf4j.{Logger, LoggerFactory}

/**
  * This consumer handles the messages sent by other civil protections in order to coordinate the rescues
  *
  * @param rabbitMQ           the channel
  * @param mainControllerImpl the main controller
  */
case class RescueTeamConsumer(private val rabbitMQ: RabbitMQ,
                              private val mainControllerImpl: MainControllerImpl)
  extends DefaultConsumer(rabbitMQ.getChannel) {

  private val Logger: Logger = LoggerFactory getLogger classOf[RescueTeamConsumer]

  override def handleDelivery(consumerTag: String,
                              envelope: Envelope,
                              properties: AMQP.BasicProperties,
                              body: Array[Byte]): Unit = {

    super.handleDelivery(consumerTag, envelope, properties, body)

    val message = new String(body, "UTF-8")
    Logger info ("[RescueTeamConsumer] " + message)

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
          myCondition = coordinator.getCondition
          myCs = coordinator.getCsName
          myTimestamp = coordinator.getReqTimestamp

          if (((CoordinatorCondition.HELD == myCondition) && reqCs == myCs) ||
            ((CoordinatorCondition.WANTED == myCondition) && reqCs == myCs && reqTimestamp.after(myTimestamp)))
            coordinator.addBlockedCP(reqFrom)
          else {
            var replyRtCondition: RescueTeamCondition = RescueTeamCondition.AVAILABLE
            val list = mainControllerImpl.model.enrolledTeamInfoList
            list forEach ((enrolledTeam: EnrolledTeamInfo) => {
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
        val replayMessage = GsonUtils.fromGson(message, classOf[ReplyCoordinationMessage])
        val replayFrom = replayMessage.getFrom
        val replayTo = replayMessage.getTo
        val replayCs = replayMessage.getRescueTeamID
        val replayRTCondition = replayMessage.getRTCondition
        myCondition = coordinator.getCondition
        myCs = coordinator.getCsName
        if (!(replayFrom == mainControllerImpl.model.cpID)) {
          if ((myCondition == CoordinatorCondition.WANTED) && myCs == replayCs && replayTo == mainControllerImpl.model.cpID) {
            if (replayRTCondition == RescueTeamCondition.AVAILABLE)
              coordinator.updatePendingCivilProtectionReplayStructure(replayFrom)
            else
              coordinator.setCondition(CoordinatorCondition.DETACHED)
          }
        }

      case MessageType.REPLY_RESCUE_TEAM_CONDITION =>
        val replyRescueTeamConditionMessage = GsonUtils.fromGson(message, classOf[ReplyRescueTeamConditionMessage])
        if (replyRescueTeamConditionMessage.getFrom != mainControllerImpl.model.cpID) {
          val rescueTeamID = replyRescueTeamConditionMessage.getRescueTeamID
          var indexToChange: Int = -1
          val infoList = mainControllerImpl.model.enrolledTeamInfoList
          infoList forEach ((enrolledTeamInfo: EnrolledTeamInfo) => {
            val enrolledTeamID = enrolledTeamInfo.teamID.value
            if (rescueTeamID == enrolledTeamID) {
              indexToChange = infoList.indexOf(enrolledTeamInfo)
            }
          })

          val rescueTeamCondition = replyRescueTeamConditionMessage.getRescueTeamCondition
          rescueTeamCondition match {

            case RescueTeamCondition.OCCUPIED =>
              if (indexToChange != -1) {
                val enrolledTeamInfo = infoList.get(indexToChange)
                mainControllerImpl.model.modifyEnrollment(indexToChange, new EnrolledTeamInfo(
                  enrolledTeamInfo.teamID.value,
                  enrolledTeamInfo.teamName.value,
                  enrolledTeamInfo.phoneNumber.value,
                  false,
                  replyRescueTeamConditionMessage.getFrom,
                  enrolledTeamInfo.alertID.value)
                )
              }
            case RescueTeamCondition.AVAILABLE =>
              if (indexToChange != -1) {
                val senderCP = replyRescueTeamConditionMessage.getFrom
                val enrolledTeamInfo = infoList.get(indexToChange)
                if (enrolledTeamInfo.cpID.value == senderCP) {
                  mainControllerImpl.model.modifyEnrollment(indexToChange, new EnrolledTeamInfo(
                    enrolledTeamInfo.teamID.value,
                    enrolledTeamInfo.teamName.value,
                    enrolledTeamInfo.phoneNumber.value,
                    true,
                    "",
                    "")
                  )
                }
              }

          }
        }

      case MessageType.REQ_RESCUE_TEAM_CONDITION =>
        val reqRescueTeamConditionMessage = GsonUtils.fromGson(message, classOf[ReqRescueTeamConditionMessage])
        if (reqRescueTeamConditionMessage.getFrom != mainControllerImpl.model.cpID) {
          val list = mainControllerImpl.model.enrolledTeamInfoList
          list forEach ((enrolledTeam: EnrolledTeamInfo) => {
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