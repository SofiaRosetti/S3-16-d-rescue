package it.unibo.drescue.connection;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.Message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RabbitMQImpl implements RabbitMQ {

    RabbitMQConnection rabbitMQConnection;
    Channel channel;

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
                try {
                    response.offer(new String(body, "UTF-8"), 15000, TimeUnit.MILLISECONDS);
                } catch (final InterruptedException e) {
                    response.offer("");
                }

            }
        });
        return response.poll(15000, TimeUnit.MILLISECONDS);
    }

}
