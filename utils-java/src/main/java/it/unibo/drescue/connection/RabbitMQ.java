package it.unibo.drescue.connection;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import it.unibo.drescue.communication.messages.Message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public interface RabbitMQ {

    void createChannel() throws IOException;

    Channel getChannel();

    String addReplyQueue() throws IOException;

    AMQP.BasicProperties setReplyTo(String queueName);

    void addQueue(String queueName) throws IOException;

    void sendMessage(String exchange, String routingKey, AMQP.BasicProperties props, Message message) throws IOException;

    String addRPCClientConsumer(BlockingQueue<String> response, String receiveQueue) throws IOException, InterruptedException;

}
