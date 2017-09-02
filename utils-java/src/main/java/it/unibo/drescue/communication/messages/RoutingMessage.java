package it.unibo.drescue.communication.messages;

/**
 * Interface modelling message with the name of the sender and receiver.
 */
public interface RoutingMessage extends Message {

    /**
     *
     * @param from the sender's name
     */
    void setFrom(String from);

    /**
     *
     * @return the sender's name
     */
    String getFrom();

    /**
     *
     * @param to the receiver's name
     */
    void setTo(String to);

    /**
     *
     * @return the receiver's name
     */
    String getTo();

}
