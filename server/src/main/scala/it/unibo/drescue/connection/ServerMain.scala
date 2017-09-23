package it.unibo.drescue.connection

/**
  * Main of server module.
  * Creates and opens rabbitMQ connection to pass it to services in
  * order to open receives channels.
  */
object ServerMain extends App {

  val connection: RabbitMQConnection = new RabbitMQConnectionImpl(RabbitConnectionConstants.LOCAL_HOST)
  connection openConnection()

  Service(connection, QueueType.MOBILEUSER_QUEUE.getQueueName, MobileuserService())
  Service(connection, QueueType.ALERTS_QUEUE.getQueueName, AlertsService())
  Service(connection, QueueType.CIVIL_PROTECTION_QUEUE.getQueueName, CivilProtectionService())

}
