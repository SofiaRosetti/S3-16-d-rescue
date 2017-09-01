package it.unibo.drescue.connection.messages.response;

/**
 * An interface modelling an error message.
 */
public interface ErrorMessage {

    /**
     * @return the error message
     */
    String getError();
}
