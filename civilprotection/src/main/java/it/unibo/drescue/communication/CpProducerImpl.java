package it.unibo.drescue.communication;

import com.rabbitmq.client.Channel;
import it.unibo.drescue.communication.messages.Message;

import java.io.IOException;

public class CpProducerImpl implements CpProducer {

    private Channel channel;

    public CpProducerImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void sendMessage(Message msg, String routingKey) throws IOException {
        System.out.println("[Send message]");
        channel.basicPublish(BrokerImpl.EXCHANGE_NAME, routingKey, null, GsonUtils.toGson(msg).getBytes("UTF-8"));
    }
}
