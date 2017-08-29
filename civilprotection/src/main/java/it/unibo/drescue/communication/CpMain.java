package it.unibo.drescue.communication;

import it.unibo.drescue.message.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;




public class CpMain {

    public static void main(String[] args) throws IOException, TimeoutException {


        //TODO Recupeare la lista delle squadre di soccorso dal server e popolare la lista rescueTeam

        String[] bindingQueue = {"RT001", "RT002",};
        String cpID = args[0];

        Broker broker = new BrokerImp();
        broker.createConnection(bindingQueue);
        broker.newConsumer();

        CpProducer producer = new CpProducerImp(broker.getChannel());

        JSONMessageFactory factory = new JSONMessageFactoryImp();
        JSONMessage msg = factory.createUpdateRescueTeamConditionMessage();
        msg.setFrom("");
        msg.setTo("");
        msg.setMessageType("");
        msg.setContent("");

        producer.sendMessage(msg, bindingQueue[1]);

        //System.out.println("cpID: " + cpID);
    }
}
