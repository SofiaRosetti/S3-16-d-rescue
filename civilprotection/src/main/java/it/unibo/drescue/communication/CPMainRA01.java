package it.unibo.drescue.communication;

import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class CPMainRA01 {


    public static void main(final String[] args) throws IOException, TimeoutException {

        //TODO Make a server request in order to get the cp's rescue team
        final String[] bindingQueue = {"RT001", "RT002", "RT003"};
        final List<String> commonCP = new ArrayList<>();
        commonCP.add("RA02");
        commonCP.add("RA03");

        final String myID = "RA01";
        final String wantedRescueTeam = args[1];
        System.out.println("myID: " + wantedRescueTeam);
        System.out.println("wanted rescue team: " + myID);

        final RabbitMQConnectionImpl connection = null;
        final RabbitMQImpl rabbitMQ = null;

        try {
            /*connection =  new RabbitMQConnectionImpl("localhost");
            connection.openConnection();
            rabbitMQ = new RabbitMQImpl(connection);
            rabbitMQ.declareExchange(CoordinatorImpl.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            final String queueName = rabbitMQ.addReplyQueue();
            //rabbitMQ.bindQueueToExchange(queueName, CoordinatorImpl.EXCHANGE_NAME, bindingQueue);

            final CPConsumer consumer = new CPConsumer(rabbitMQ.getChannel());
            consumer.setCpID(myID);
            rabbitMQ.addConsumer(consumer, queueName);

            Coordinator coordinator = CoordinatorImpl.getInstance();
            coordinator.setConnection(rabbitMQ);

            //Coordinator configuration
            coordinator.setCondition(CoordinatorCondition.WANTED);
            coordinator.setCsName(wantedRescueTeam);
            coordinator.setMyID(myID);
            coordinator.setReqTimestamp(new Timestamp(System.currentTimeMillis()));
            coordinator.createPendingCivilProtectionReplayStructure(commonCP);

            //Broadcast request
            ReqCoordinationMessage reqCoordinationMessage = new ReqCoordinationMessage();
            reqCoordinationMessage.setFrom(myID);
            reqCoordinationMessage.setRescueTeamID(wantedRescueTeam);
            reqCoordinationMessage.setTimestamp(coordinator.getReqTimestamp());

            rabbitMQ.sendMessage(CoordinatorImpl.EXCHANGE_NAME,  wantedRescueTeam, null, reqCoordinationMessage);

            //The process is blocked as long as enter in cs (coordinator condition == HELD) or has received a negative responde (RS = OCCUPIED)
            while (coordinator.getCondition() != CoordinatorCondition.HELD && coordinator.getCondition() != CoordinatorCondition.DETACHED){
                System.out.println(coordinator.getCondition());
            }
            if (coordinator.getCondition() == CoordinatorCondition.HELD){
                //TODO CS execution
                System.out.println("[CS Execution]");
                //the process came back from cs
                coordinator.backToCs();
            }*/
        } catch (final Exception e) {
            //TODO
            e.printStackTrace();
        }


    }

}
