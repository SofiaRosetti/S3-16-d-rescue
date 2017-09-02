package it.unibo.drescue.connection.client;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class that handles the sending of requests.
 */
public class RPCSenderImpl implements RPCSender {

    Channel channel;
    String receiverChannelName;
    String replyQueueName;

    /**
     * Creates and waits for a response on the specific connection.
     *
     * @param connection          the connection on which open the channel
     * @param receiverChannelName the channel name on which to send the request
     */
    public RPCSenderImpl(final Connection connection, final String receiverChannelName) {

        try {
            this.channel = connection.createChannel();
            this.replyQueueName = this.channel.queueDeclare().getQueue();
        } catch (final IOException e) {
            e.printStackTrace();
            //TODO handle
        }
        this.receiverChannelName = receiverChannelName;

    }

    @Override
    public String doRequest(final String jsonMessage) {
        final String corrId = UUID.randomUUID().toString();

        final AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(this.replyQueueName)
                .build();

        try {
            this.channel.basicPublish("", this.receiverChannelName, props, jsonMessage.getBytes("UTF-8"));
        } catch (final IOException e) {
            e.printStackTrace();
            //TODO handle
        }

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        try {
            this.channel.basicConsume(this.replyQueueName, true, new DefaultConsumer(RPCSenderImpl.this.channel) {
                @Override
                public void handleDelivery(final String consumerTag, final Envelope envelope,
                                           final AMQP.BasicProperties properties, final byte[] body) throws IOException {
                    if (properties.getCorrelationId().equals(corrId)) {
                        response.offer(new String(body, "UTF-8"));
                    }
                }
            });
        } catch (final IOException e) {
            e.printStackTrace();
            //TODO handle
        }

        try {
            return response.take();
        } catch (final InterruptedException e) {
            e.printStackTrace();
            //TODO handle
        }

        return null;
    }


}
