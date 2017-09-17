package it.unibo.drescue.communication;

import com.rabbitmq.client.BuiltinExchangeType;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;
import it.unibo.drescue.utils.Coordinator;
import it.unibo.drescue.utils.CoordinatorCondition;
import it.unibo.drescue.utils.CoordinatorImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class CPMainRA03 {



    public static void main(final String[] args) throws IOException, TimeoutException {

        //TODO Make a server request in order to get the cp's rescue team
        final String[] bindingQueue = {"RT001", "RT002", "RT003"};
        final List<String> commonCP = new ArrayList<>();
        commonCP.add("RA01");
        commonCP.add("RA02");

        final String myID = "RA03";
        final String wantedRescueTeam = args[1];
        System.out.println("wanted rescue team: "+ wantedRescueTeam);
        System.out.println("myID: "+ myID);

        RabbitMQConnectionImpl connection = null;
        RabbitMQImpl rabbitMQ = null;

        try {

            connection =  new RabbitMQConnectionImpl("localhost");
            connection.openConnection();
            rabbitMQ = new RabbitMQImpl(connection);
            rabbitMQ.declareExchange(CoordinatorImpl.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            final String queueName = rabbitMQ.addReplyQueue();
            rabbitMQ.bindQueueToExchange(queueName, CoordinatorImpl.EXCHANGE_NAME, bindingQueue);

            final CPConsumer consumer = new CPConsumer(rabbitMQ.getChannel());
            consumer.setCpID(myID);
            rabbitMQ.addConsumer(consumer, queueName);

            Coordinator coordinator = CoordinatorImpl.getInstance();
            coordinator.setConnection(rabbitMQ);
            coordinator.setCondition(CoordinatorCondition.DETACHED);


            System.out.println();
            coordinator.setMyID(myID);




        }
        catch (Exception e){

        }


    }

}
