package it.unibo.drescue.communication;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.*;
import it.unibo.drescue.communication.messages.CPConfigurationMessage;
import it.unibo.drescue.communication.messages.CPCoordinationMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BrokerImpl implements Broker {

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
        //TODO Refactor Consumer into new java class and delete consolo log
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("received message :[ "+ msg+ " ]");
                JsonElement je = new JsonParser().parse(msg);
                JsonObject jo = je.getAsJsonObject();
                String messageType = jo.get("messageType").getAsString();

                switch (messageType){
                    case CPCoordinationMessage.COORDINATION_MESSAGE:
                        CPCoordinationMessage cpCoordinationMessage = GsonUtils.fromGson(msg, CPCoordinationMessage.class);
                        System.out.println("RescueTeam name: "+ cpCoordinationMessage.getRescueTeam().getName());
                        System.out.println("From: "+ cpCoordinationMessage.getFrom());
                        System.out.println("To: "+ cpCoordinationMessage.getTo());
                    break;
                    case CPConfigurationMessage.CONFIGURATION_MESSAGE:
                        CPConfigurationMessage cpConfigurationMessage = GsonUtils.fromGson(msg, CPConfigurationMessage.class);
                        System.out.println("RescueTeam name: "+ cpConfigurationMessage.getRescueTeamCollection().get(0).getName());
                        System.out.println("From: "+ cpConfigurationMessage.getFrom());
                        System.out.println("To: "+ cpConfigurationMessage.getTo());
                    break;
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);
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
