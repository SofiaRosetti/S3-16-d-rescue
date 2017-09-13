package it.unibo.drescue.communication.messages;

public class ReplayCoordinationMessage extends CoordinationMessage {
    /**
     * Creates a simple routing message of the given type.
     *
     * @param messageType type of the message
     */
    public ReplayCoordinationMessage(MessageType messageType) {
        super(messageType.REPLAY_COORDINATION_MESSAGE);
    }
}
