package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a message for performing login.
 */
public interface LoginMessage {

    /**
     * @return user's email
     */
    String getEmail();

    /**
     * @return user's password
     */
    String getPassword();
}
