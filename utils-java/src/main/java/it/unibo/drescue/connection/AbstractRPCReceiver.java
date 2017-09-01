package it.unibo.drescue.connection;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;

/**
 * Abstract class that waits to receive a message.
 */
public abstract class AbstractRPCReceiver implements RPCReceiver {

    private Channel channel;

    /**
     * Creates and waits for a message on the specific connection and channel name.
     *
     * @param connection         the connection on which open the channel
     * @param receiveChannelName the channel name on which waiting for messages.
     */
    public AbstractRPCReceiver(final Connection connection, final String receiveChannelName) {

        try {
            this.channel = connection.createChannel();
            this.channel.queueDeclare(receiveChannelName, false, false, false, null);
            this.channel.basicQos(1);


            final QueueingConsumer consumer = new QueueingConsumer(this.channel);
            this.channel.basicConsume(receiveChannelName, false, consumer);

            while (true) {

                String response = null;

                final QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                final AMQP.BasicProperties props = delivery.getProperties();
                final AMQP.BasicProperties replyProps =
                        new AMQP.BasicProperties.Builder()
                                .correlationId(props.getCorrelationId())
                                .build();

                try {
                    final String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received: " + message);

                    response = accessDB(message);

                } catch (final Exception e) {
                    e.printStackTrace();
                    response = GsonUtils.toGson(new ErrorMessageImpl("Exception."));

                } finally {
                    this.channel.basicPublish("", props.getReplyTo(), replyProps,
                            response.getBytes("UTF-8"));
                    this.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            }

        } catch (final Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final Exception e) {
                }
            }
        }
    }

    @Override
    public abstract String accessDB(String jsonMessage);
}
