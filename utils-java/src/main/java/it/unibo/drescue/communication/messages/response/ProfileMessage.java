package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.model.User;

/**
 * Interface modelling a response message containing user data of profile.
 */
public interface ProfileMessage {

    /**
     * @return user data of profile
     */
    User getProfile();

}
