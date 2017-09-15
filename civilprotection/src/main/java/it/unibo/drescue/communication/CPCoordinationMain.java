package it.unibo.drescue.communication;

import com.rabbitmq.client.BuiltinExchangeType;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;
import it.unibo.drescue.utils.Coordinator;
import it.unibo.drescue.utils.CoordinatorCondition;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CPCoordinationMain {

    public final static String EXCHANGE_NAME = "rabbitmq_rescueteam";


    public static void main(final String[] args) throws IOException, TimeoutException {

        //TODO Make a server request in order to get the cp's rescue team
        final String[] bindingQueue = {"RT001", "RT002"};

        final String[] commonCP = {"RA01", "RA02"};

        RabbitMQConnectionImpl connection = null;
        RabbitMQImpl rabbitMQ = null;

        try {

            connection =  new RabbitMQConnectionImpl("localhost");
            connection.openConnection();
            rabbitMQ = new RabbitMQImpl(connection);

            rabbitMQ.declareExchange(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            final String queueName = rabbitMQ.addReplyQueue();
            rabbitMQ.bindQueueToExchange(queueName, EXCHANGE_NAME, bindingQueue);

            final CPConsumer consumer = new CPConsumer(rabbitMQ.getChannel());
            rabbitMQ.addConsumer(consumer, queueName);

            //rabbitMQ.sendMessage(EXCHANGE_NAME,  bindingQueue[1], null, null);




            //TODO inviare un message broadcast alle CP che condividono il RescueTeam che voglio impegnare (=spedirlo nella coda con routingKey = RTID)





        }
        catch (Exception e){
            //TODO
        }


    }

}
