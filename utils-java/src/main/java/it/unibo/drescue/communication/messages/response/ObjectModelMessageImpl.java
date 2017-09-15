package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.ObjectModel;

/**
 * Class that represents a message containing an object model.
 */
public class ObjectModelMessageImpl extends AbstractMessage implements ObjectModelMessage {

    private final ObjectModel objectModel;

    /**
     * Creates a message containing the given object model.
     *
     * @param objectModel the object to send
     */
    public ObjectModelMessageImpl(final ObjectModel objectModel) {
        super(MessageType.OBJECT_MODEL_MESSAGE);
        this.objectModel = objectModel;
    }

    @Override
    public ObjectModel getObjectModel() {
        return this.objectModel;
    }
}
