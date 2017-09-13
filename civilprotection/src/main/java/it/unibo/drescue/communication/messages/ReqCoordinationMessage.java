package it.unibo.drescue.communication.messages;

public class ReqCoordinationMessage extends CoordinationMessage {


    /**
     * Creates a simple routing message of the given type.
     *
     * @param messageType type of the message
     */
    public ReqCoordinationMessage(MessageType messageType) {
        super(messageType.REQ_COORDINATION_MESSAGE);
    }
}
