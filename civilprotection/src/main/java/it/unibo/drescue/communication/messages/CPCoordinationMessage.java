package it.unibo.drescue.communication.messages;

import it.unibo.drescue.model.RescueTeam;
import it.unibo.drescue.model.RescueTeamImpl;

public class CPCoordinationMessage extends AbstractRoutingMessage {

    public final static String COORDINATION_MESSAGE = "coordination_message";

    private String messageType;
    private RescueTeamImpl rescueTeam;

    public RescueTeam getRescueTeam() {
        return rescueTeam;
    }

    public void setRescueTeam(RescueTeamImpl rescueTeam) {
        this.rescueTeam = rescueTeam;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType() {
        this.messageType = COORDINATION_MESSAGE;
    }
}
