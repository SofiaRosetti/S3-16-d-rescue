package it.unibo.drescue.communication;

import com.rabbitmq.client.Channel;
import it.unibo.drescue.message.JSONMessage;

import java.io.IOException;

public class CpProducerImp implements CpProducer {

    private Channel channel;

    public CpProducerImp(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void sendMessage(JSONMessage msg, String routingKey) throws IOException {
        System.out.println("Send message");
        channel.basicPublish(BrokerImp.EXCHANGE_NAME, routingKey, null, msg.toJSON().getBytes());
    }
}
