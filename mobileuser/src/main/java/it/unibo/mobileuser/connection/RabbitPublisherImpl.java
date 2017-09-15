package it.unibo.mobileuser.connection;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.connection.RabbitMQConnectionImpl;
import it.unibo.drescue.connection.RabbitMQImpl;

import java.io.IOException;

public class RabbitPublisherImpl implements RabbitPublisher {

    private final String destinationQueue;

    public RabbitPublisherImpl(final String destinationQueue) {
        this.destinationQueue = destinationQueue;
    }

    @Override
    public boolean publish(final Message message) {

        boolean successfulPublish;

        final RabbitMQConnectionImpl connection = new RabbitMQConnectionImpl("10.0.2.2");
        connection.openConnection();

        final RabbitMQImpl rabbitMQ;
        try {
            rabbitMQ = new RabbitMQImpl(connection);
            rabbitMQ.sendMessage("", this.destinationQueue, null, message);
            successfulPublish = true;
        } catch (final IOException e) {
            successfulPublish = false;
        } finally {
            if (connection.getConnection() != null) {
                connection.closeConnection();
            }
        }
        return successfulPublish;
    }


}
