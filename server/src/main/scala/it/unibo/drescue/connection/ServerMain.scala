package it.unibo.drescue.connection

/**
  * Main of server module.
  * Creates and opens rabbitMQ connection to pass it to services in
  * order to open receives channels.
  */
object ServerMain extends App {

  val connection: RabbitMQConnection = new RabbitMQConnectionImpl("localhost")
  connection openConnection()

  Service(connection, QueueType.MOBILEUSER_QUEUE.getQueueName, MobileuserService())
  Service(connection, QueueType.ALERTS_QUEUE.getQueueName, AlertsService())

  //TODO
  // AlertsService
  // CivilProtectionService

}
