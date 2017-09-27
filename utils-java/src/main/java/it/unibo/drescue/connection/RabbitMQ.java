package it.unibo.drescue.connection;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Interface modelling operations that can be invoked on RabbitMQ connections.
 */
public interface RabbitMQ {

    /**
     * Creates a channel (virtual connection) over the real TCP connection.
     *
     * @throws IOException if an error is encountered
     */
    void createChannel() throws IOException;

    /**
     * Gets the channel created over the real TCP connection.
     *
     * @return the channel
     */
    Channel getChannel();

    /**
     * Creates an exclusive queue on the Java client.
     *
     * @return the queue name
     * @throws IOException if an error is encountered
     */
    String addReplyQueue() throws IOException;

    /**
     * Sets the callback queue on which to wait for the response.
     *
     * @param queueName queue on which to wait for the response
     * @return AMQP.BasicProperties with the callback queue setted
     */
    AMQP.BasicProperties setReplyTo(String queueName);

    /**
     * Declare a queue.
     *
     * @param queueName the queue name
     * @throws IOException if an error is encountered
     */
    void addQueue(String queueName) throws IOException;

    /**
     * Publish the given message using the given exchange and routing key.
     *
     * @param exchange   empty exchange for client-server communication, full for client-client communication
     * @param routingKey queue name on which to publish the message
     * @param props      null props for only publish, full for receiving response
     * @param message    message to send
     * @throws IOException if an error is encountered
     */
    void sendMessage(String exchange, String routingKey, AMQP.BasicProperties props, Message message) throws IOException;

    /**
     * Add a client side consumer waiting for response.
     *
     * @param response     queue from which retreive the response
     * @param receiveQueue queue name on which to wait for messages
     * @return the response or null if timeout is reached
     * @throws IOException          if an error is encountered
     * @throws InterruptedException if an error interrupt the activity
     */
    String addRPCClientConsumer(BlockingQueue<String> response, String receiveQueue) throws IOException, InterruptedException;


    /**
     * Add a new consumer on the channel
     *
     * @param consumer  the consumer
     * @param queueName the name of the queue
     * @throws IOException if an error is encountered
     */
    void addConsumer(Consumer consumer, String queueName) throws IOException;

    /**
     * declare a new exchange with no extra arguments
     *
     * @param exchangeName the name of the exchange
     * @param exchangeType the exchange type
     * @throws IOException if an error is encountered
     */
    void declareExchange(String exchangeName, BuiltinExchangeType exchangeType) throws IOException;


    /**
     * Bind a queue to an exchange using a list of routing keys.
     *
     * @param queueName       the name of the queue
     * @param exchange        the name of the exchange
     * @param routingKeysList the routing key to use for the binding
     * @throws IOException if an error is encountered
     */
    void bindQueueToExchange(String queueName, String exchange, List<RescueTeamImpl> routingKeysList) throws IOException;

    /**
     * Bind a queue to an exchange using a routing key.
     *
     * @param queueName  the name of the queue
     * @param exchange   the name of the exchange
     * @param routingKey the routing key to use for the binding
     * @throws IOException if an error is encountered
     */
    void bindQueueToExchange(String queueName, String exchange, RescueTeamImpl routingKey) throws IOException;

}
