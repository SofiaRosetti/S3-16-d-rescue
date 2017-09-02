package it.unibo.drescue.communication.builder;

/**
 * Interface modelling a generic message builder with sender and receiver information.
 */
public interface RoutingMessageBuilder extends MessageBuilder {

    /**
     *
     * @param from the sender's name
     * @return the message builder
     */
    RoutingMessageBuilder setFrom(final String from);

    /**
     *
     * @param to the receiver's name
     * @return the message builder
     */
    RoutingMessageBuilder setTo(final String to);
}
