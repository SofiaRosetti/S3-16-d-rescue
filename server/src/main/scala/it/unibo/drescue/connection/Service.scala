package it.unibo.drescue.connection

/**
  * Class that represents a new service with a consumer handling requests
  * on the given connection and the given queue.
  *
  * @param rabbitMQConnection connection on which create the service
  * @param receiveQueueName   queue name on which consume messages
  * @param serviceOperation   handler for requests
  */
case class Service(rabbitMQConnection: RabbitMQConnection,
                   receiveQueueName: String,
                   serviceOperation: ServiceOperation) {

  val rabbitMQ: RabbitMQ = new RabbitMQImpl(rabbitMQConnection)
  rabbitMQ addQueue receiveQueueName
  rabbitMQ addConsumer(ServerConsumer(rabbitMQ, serviceOperation), receiveQueueName)

}


