package it.unibo.drescue.communication;

import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilder;
import it.unibo.drescue.communication.builder.CPConfigurationMessageBuilderImpl;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilder;
import it.unibo.drescue.communication.builder.CPCoordinationMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;




public class CpMain {

    public static void main(String[] args) throws IOException, TimeoutException {


        //TODO Make a server request in order to get the cp's rescue team
        String[] bindingQueue = {"RT001", "RT002",};
        String cpID = args[0];

        Broker broker = new BrokerImpl();
        broker.createConnection(bindingQueue);
        broker.newConsumer();

        CpProducer producer = new CpProducerImpl(broker.getChannel());

        //*************************************************************
        //TODO Insert into Test class
        RescueTeamImpl rescueTeamRA01 = new RescueTeamImpl("Ra01","Ra01", "Ra01" , 1254, 45489, "123452");
        RescueTeamImpl rescueTeamRA02 = new RescueTeamImpl("Ra02","Ra02", "Ra02" , 1254, 45489, "789258");

        List<RescueTeamImpl> rescueTeamCollection = new ArrayList<>();
        rescueTeamCollection.add(rescueTeamRA01);
        rescueTeamCollection.add(rescueTeamRA02);

        CPCoordinationMessageBuilder coordinationBuilder = new CPCoordinationMessageBuilderImpl();

        Message message =  coordinationBuilder
                .setRescueTeam(rescueTeamRA01)
                .setFrom("Martina")
                .setTo("Anna")
                .setMessageType()
                .build();

        producer.sendMessage(message, bindingQueue[1]);


        CPConfigurationMessageBuilder configurationBuilder = new CPConfigurationMessageBuilderImpl();

        Message m = configurationBuilder
                .setRescueTeamCollection(rescueTeamCollection)
                .setFrom("Martina")
                .setTo("Anna")
                .setMessageType()
                .build();

        producer.sendMessage(m, bindingQueue[1]);

    }
}
