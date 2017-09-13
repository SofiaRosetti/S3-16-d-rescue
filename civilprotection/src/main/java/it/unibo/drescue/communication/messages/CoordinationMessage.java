package it.unibo.drescue.communication.messages;

import java.sql.Timestamp;

public class CoordinationMessage extends AbstractRoutingMessage {

    private Timestamp timestamp;
    private String rescueTeamID;

    /**
     * Creates a simple routing message of the given type.
     *
     * @param messageType type of the message
     */
    public CoordinationMessage(MessageType messageType) {
        super(messageType);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getRescueTeamID() {
        return rescueTeamID;
    }

    public void setRescueTeamID(final String rescueTeamID) {
        this.rescueTeamID = rescueTeamID;
    }
}
