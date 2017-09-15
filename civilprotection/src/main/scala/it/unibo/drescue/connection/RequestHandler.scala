package it.unibo.drescue.connection

import java.util.concurrent.Callable

import it.unibo.drescue.communication.messages.Message

class RequestHandler(val channel: RabbitMQImpl, val message: Message) extends Callable[String] {

  override def call() = {
    val string: String = "ok"
    //TODO request channel.send message and wait response
    //TODO return response
    Thread.sleep(1000)
    string
  }

}
