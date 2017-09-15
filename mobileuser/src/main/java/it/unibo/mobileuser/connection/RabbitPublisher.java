package it.unibo.mobileuser.connection;

import it.unibo.drescue.communication.messages.Message;

/**
 * Interface modelling a simple publisher.
 */
public interface RabbitPublisher {

    /**
     * Publish a message over rabbitMQ.
     *
     * @param message message to publish
     * @return true if the message was correctly published, false if an error occurr
     */
    boolean publish(Message message);

}
