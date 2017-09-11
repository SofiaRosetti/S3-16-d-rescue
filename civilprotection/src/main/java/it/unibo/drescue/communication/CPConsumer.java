package it.unibo.drescue.communication;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import it.unibo.drescue.communication.messages.CPConfigurationMessage;
import it.unibo.drescue.communication.messages.CPCoordinationMessage;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.MessageUtils;

import java.io.IOException;

public class CPConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public CPConsumer(final Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(final String consumerTag,
                               final Envelope envelope,
                               final AMQP.BasicProperties properties,
                               final byte[] body)
            throws IOException {
        final String msg = new String(body, "UTF-8");
        System.out.println("received message :[ " + msg + " ]");
        final MessageType nameMessage = MessageUtils.getMessageNameByJson(msg);

        //TODO Refactor
        switch (nameMessage) {
            case COORDINATION_MESSAGE:
                final CPCoordinationMessage cpCoordinationMessage = GsonUtils.fromGson(msg, CPCoordinationMessage.class);
                System.out.println("RescueTeam name: " + cpCoordinationMessage.getRescueTeam().getName());
                System.out.println("From: " + cpCoordinationMessage.getFrom());
                System.out.println("To: " + cpCoordinationMessage.getTo());
                break;
            case CONFIGURATION_MESSAGE:
                final CPConfigurationMessage cpConfigurationMessage = GsonUtils.fromGson(msg, CPConfigurationMessage.class);
                System.out.println("RescueTeam name: " + cpConfigurationMessage.getRescueTeamCollection().get(0).getName());
                System.out.println("From: " + cpConfigurationMessage.getFrom());
                System.out.println("To: " + cpConfigurationMessage.getTo());
                break;
        }
    }
}
