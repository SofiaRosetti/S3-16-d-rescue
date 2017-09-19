package it.unibo.drescue.connection

import com.rabbitmq.client.DefaultConsumer
import it.unibo.drescue.database.DBConnectionImpl
import it.unibo.drescue.geocoding.GeocodingException
import org.slf4j.{Logger, LoggerFactory}

/**
  * Class that represents a general server side consumer.
  *
  * @param rabbitMQ         connection to rabbitMQ
  * @param serviceOperation delegate with methods to handle requests and do right actions
  *                         (create response message or forward it)
  */
case class ServerConsumer(private val rabbitMQ: RabbitMQ,
                          private val serviceOperation: ServiceOperation)
  extends DefaultConsumer(rabbitMQ.getChannel) {

  val dbConnection: DBConnectionImpl = DBConnectionImpl.getLocalConnection
  val Logger: Logger = LoggerFactory getLogger classOf[Service]

  import com.rabbitmq.client.{AMQP, Envelope}

  override def handleDelivery(consumerTag: String,
                              envelope: Envelope,
                              properties: AMQP.BasicProperties,
                              body: Array[Byte]): Unit = {
    super.handleDelivery(consumerTag, envelope, properties, body)

    val message = new String(body, "UTF-8")
    Logger info ("ServerConsumer: " + message)

    import it.unibo.drescue.communication.messages.Message
    import it.unibo.drescue.database.exceptions._

    var response: Option[Message] = None
    try {
      response = serviceOperation.accessDB(dbConnection, message)
    } catch {
      case conn: DBConnectionException => Logger error("DBConnectionException on " + message, conn)
      case notFound: DBNotFoundRecordException => Logger error("DBNotFoundRecordException on " + message, notFound)
      case query: DBQueryException => Logger error("DBQueryException on " + message, query)
      case duplicated: DBDuplicatedRecordException => Logger error("DBDuplicatedRecordException on " + message, duplicated)
      case geocoding: GeocodingException => Logger error("GeocodingException on " + message, geocoding)
      case e: Exception => Logger error("Unknown Exception", e)
    }

    Logger info ("ServerConsumer: Response = " + response)

    response match {
      case Some(response) => serviceOperation.handleDBresult(rabbitMQ, properties, response)
      case None => //do nothing
    }

  }

}
