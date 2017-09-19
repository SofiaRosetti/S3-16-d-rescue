package it.unibo.drescue.connection;

import com.rabbitmq.client.*;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.Message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Class modelling operations that can be invoked on RabbitMQ connections.
 */
public class RabbitMQImpl implements RabbitMQ {

    private final RabbitMQConnection rabbitMQConnection;
    private Channel channel;

    /**
     * Constructor.
     *
     * @param rabbitMQConnection the RabbitMQ connection
     * @throws IOException if an error is encountered
     */
    public RabbitMQImpl(final RabbitMQConnection rabbitMQConnection) throws IOException {
        this.rabbitMQConnection = rabbitMQConnection;
        createChannel();
    }

    @Override
    public void createChannel() throws IOException {
        this.channel = this.rabbitMQConnection.getConnection().createChannel();
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public String addReplyQueue() throws IOException {
        return this.channel.queueDeclare().getQueue();
    }

    @Override
    public AMQP.BasicProperties setReplyTo(final String queueName) {
        return new AMQP.BasicProperties
                .Builder()
                .replyTo(queueName)
                .build();
    }

    @Override
    public void addQueue(final String queueName) throws IOException {
        this.channel.queueDeclare(queueName, false, false, false, null);
    }

    @Override
    public void sendMessage(final String exchange, final String routingKey, final AMQP.BasicProperties props, final Message message) throws IOException {
        this.channel.basicPublish(exchange, routingKey, props, GsonUtils.toGson(message).getBytes("UTF-8"));
    }

    @Override
    public String addRPCClientConsumer(final BlockingQueue<String> response, final String receiveQueue) throws IOException, InterruptedException {

        this.channel.basicConsume(receiveQueue, new DefaultConsumer(this.channel) {
            @Override
            public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body) throws IOException {
                response.offer(new String(body, "UTF-8"));
            }
        });
        return response.poll(15000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void addConsumer(final Consumer consumer, final String queueName) throws IOException {
        this.channel.basicConsume(queueName, true, consumer);
    }


    @Override
    public void declareExchange(final String exchangeName, final BuiltinExchangeType exchangeType) throws IOException {
        this.channel.exchangeDeclare(exchangeName, exchangeType);
    }

    @Override
    public void bindQueueToExchange(final String queue, final String exchange, final String[] routingKeys) throws IOException {
        for (final String routingKey : routingKeys) {
            this.channel.queueBind(queue, exchange, routingKey);
        }
    }

}
