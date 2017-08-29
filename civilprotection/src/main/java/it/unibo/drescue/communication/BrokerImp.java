package it.unibo.drescue.communication;

import com.rabbitmq.client.*;
import it.unibo.drescue.message.JSONMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BrokerImp implements Broker {

    public final static String EXCHANGE_NAME = "rabbitmq_rescueteam";

    private Channel channel;
    private String queueName;

    @Override
    public void createConnection(String[] bindingQueue) throws IOException, TimeoutException {
        //TODO See Authentication Mechanisms
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        queueName = channel.queueDeclare().getQueue();

        //One binding for each rescueTeam
        for(String routingKey : bindingQueue){
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }
    }

    @Override
    public void newConsumer() throws IOException {
        //TODO Refactor Consumer into new java class
        Consumer consumer2 = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                JSONMessage cpMsgReceived = JSONMessage.fromJSON(msg.toString());
                System.out.println("Received: " + cpMsgReceived);
                if (!cpMsgReceived.getFrom().equals("")){
                    //TODO update RescueTeam condition

                }
            }
        };
        channel.basicConsume(queueName, true, consumer2);
    }


    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public String getQueueName() {
        return queueName;
    }


}
