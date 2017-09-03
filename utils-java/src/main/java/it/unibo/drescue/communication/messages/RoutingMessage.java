package it.unibo.drescue.communication.messages;

/**
 * Interface modelling message with the name of the sender and receiver.
 */
public interface RoutingMessage {

    /**
     *
     * @return the sender's name
     */
    String getFrom();

    /**
     *
     * @param from the sender's name
     */
    void setFrom(String from);

    /**
     *
     * @return the receiver's name
     */
    String getTo();

    /**
     *
     * @param to the receiver's name
     */
    void setTo(String to);

}
