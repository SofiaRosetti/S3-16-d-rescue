package it.unibo.drescue.connection;

import com.rabbitmq.client.*;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;

import java.io.IOException;

/**
 * Abstract class that waits to receive a message.
 */
public abstract class AbstractRPCReceiver implements RPCReceiver {

    private Channel channel;

    /**
     * Creates and waits for a message on the specific connection and channel name.
     *
     * @param connection       the connection on which open the channel
     * @param receiveQueueName the queue name on which waiting for messages.
     */
    public AbstractRPCReceiver(final Connection connection, final String receiveQueueName) {

        try {
            this.channel = connection.createChannel();
            this.channel.queueDeclare(receiveQueueName, false, false, false, null);
            this.channel.basicQos(1);

            final Consumer consumer = new DefaultConsumer(this.channel) {
                @Override
                public void handleDelivery(final String consumerTag, final Envelope envelope,
                                           final AMQP.BasicProperties properties, final byte[] body)
                        throws IOException {

                    super.handleDelivery(consumerTag, envelope, properties, body);

                    String response = "";

                    try {
                        final String message = new String(body, "UTF-8");
                        System.out.println("[AbstractRPCReceiver] Received: " + message);

                        response = accessDB(message);

                    } catch (final Exception e) {
                        response = GsonUtils.toGson(new ErrorMessageImpl("Exception."));
                    } finally {
                        AbstractRPCReceiver.this.channel.basicPublish("", properties.getReplyTo(), null,
                                response.getBytes("UTF-8"));
                        AbstractRPCReceiver.this.channel.basicAck(envelope.getDeliveryTag(), false);
                    }

                }
            };

            this.channel.basicConsume(receiveQueueName, false, consumer);

        } catch (final Exception e) {
            e.printStackTrace();
            //TODO handle

        }
    }

    @Override
    public abstract String accessDB(String jsonMessage);

}
