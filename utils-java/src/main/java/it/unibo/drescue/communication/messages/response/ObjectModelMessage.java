package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.model.ObjectModel;

/**
 * Interface modelling a message containing an object model.
 */
public interface ObjectModelMessage {

    /**
     * @return the object model
     */
    ObjectModel getObjectModel();
}
