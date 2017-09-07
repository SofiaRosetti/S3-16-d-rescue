package it.unibo.drescue.communication;

import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilder;
import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilderImpl;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilder;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;
import it.unibo.drescue.model.RescueTeamImplBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class CPMain {

    public static void main(final String[] args) throws IOException, TimeoutException {


        //TODO Make a server request in order to get the cp's rescue team
        final String[] bindingQueue = {"RT001", "RT002",};
        final String cpID = args[0];

        final Broker broker = new BrokerImpl();
        broker.createConnection(bindingQueue);
        final CPConsumer consumer = new CPConsumer(broker.getChannel());
        broker.newConsumer(consumer);

        final CPProducer producer = new CPProducerImpl(broker.getChannel());

        //*************************************************************
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

        final Message message = coordinationBuilder
                .setRescueTeam(rescueTeamRA01)
                .setFrom("Martina")
                .setTo("Anna")
                .build();


        producer.sendMessage(message, bindingQueue[1]);

        final CPConfigurationMessageBuilder configurationBuilder = new CPConfigurationMessageBuilderImpl();

        final Message m = configurationBuilder
                .setRescueTeamCollection(rescueTeamCollection)
                .setFrom("Martina")
                .setTo("Anna")
                .build();

        producer.sendMessage(m, bindingQueue[1]);

    }
}
