package it.unibo.drescue.communication;

import com.rabbitmq.client.BuiltinExchangeType;
import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilder;
import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilderImpl;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilder;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;
import it.unibo.drescue.model.RescueTeamImpl;
import it.unibo.drescue.model.RescueTeamImplBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class CPMain {

    public final static String EXCHANGE_NAME = "rabbitmq_rescueteam";

    public static void main(final String[] args) throws IOException, TimeoutException {


        //TODO Insert into Test class
        final RescueTeamImpl rescueTeamRA01 = new RescueTeamImplBuilder()
                .setRescueTeamID("Ra01")
                .setPassword("Ra01")
                .setName("Ra01")
                .setLatitude(1254)
                .setLongitude(45489)
                .setPhoneNumber("123452")
                .createRescueTeamImpl();

        final RescueTeamImpl rescueTeamRA02 = new RescueTeamImplBuilder()
                .setRescueTeamID("Ra012")
                .setPassword("Ra02")
                .setName("Ra02")
                .setLatitude(1254)
                .setLongitude(45489)
                .setPhoneNumber("789258")
                .createRescueTeamImpl();


        final List<RescueTeamImpl> rescueTeamCollection = new ArrayList<>();
        rescueTeamCollection.add(rescueTeamRA01);
        rescueTeamCollection.add(rescueTeamRA02);

        final CPCoordinationMessageBuilder coordinationBuilder = new CPCoordinationMessageBuilderImpl();

        final Message coordinationMessage = coordinationBuilder
                .setRescueTeam(rescueTeamRA01)
                .setFrom("Martina")
                .setTo("Anna")
                .build();

        final CPConfigurationMessageBuilder configurationBuilder = new CPConfigurationMessageBuilderImpl();

        final Message configurationMessage = configurationBuilder
                .setRescueTeamCollection(rescueTeamCollection)
                .setFrom("Martina")
                .setTo("Anna")
                .build();



        //TODO Make a server request in order to get the cp's rescue team
        final String[] bindingQueue = {"RT001", "RT002"};

        RabbitMQConnectionImpl connection = null;
        RabbitMQImpl rabbitMQ = null;


        try {

            connection =  new RabbitMQConnectionImpl("localhost");
            connection.openConnection();
            rabbitMQ = new RabbitMQImpl(connection);

            rabbitMQ.declareExchange(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);


            final String queueName = rabbitMQ.addReplyQueue();
            rabbitMQ.bindQueueToExchange(queueName, EXCHANGE_NAME, rescueTeamCollection);

            /*
            final CPConsumer consumer = new CPConsumer(rabbitMQ.getChannel());
            rabbitMQ.addConsumer(consumer, queueName);
            */

            rabbitMQ.sendMessage(EXCHANGE_NAME,  bindingQueue[1], null, coordinationMessage);
            rabbitMQ.sendMessage(EXCHANGE_NAME,  bindingQueue[0], null, configurationMessage);


        }
        catch (Exception e){
            //TODO
        }


    }

}
