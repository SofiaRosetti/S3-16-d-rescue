package it.unibo.drescue.communication;

import com.rabbitmq.client.*;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.messages.CPConfigurationMessage;
import it.unibo.drescue.communication.messages.CPCoordinationMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BrokerImpl implements Broker {

    public final static String EXCHANGE_NAME = "rabbitmq_rescueteam";

    private Channel channel;
    private String queueName;

    @Override
    public void createConnection(final String[] bindingQueue) throws IOException, TimeoutException {
        //TODO See Authentication Mechanisms
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        final Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        this.queueName = this.channel.queueDeclare().getQueue();

        //One binding for each rescueTeam
        for (final String routingKey : bindingQueue) {
            this.channel.queueBind(this.queueName, EXCHANGE_NAME, routingKey);
        }
    }

    @Override
    public void newConsumer(Consumer consumer) throws IOException {
        //TODO Refactor Consumer into new java class and delete consolo log


        this.channel.basicConsume(this.queueName, true, consumer);
    }


    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public String getQueueName() {
        return this.queueName;
    }


}
