package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a request message for obtaining user profile.
 */
public interface RequestProfileMessage {

    /**
     * @return user's identifier
     */
    int getUserID();
}
