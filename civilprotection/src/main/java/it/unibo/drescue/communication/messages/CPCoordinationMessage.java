package it.unibo.drescue.communication.messages;

import it.unibo.drescue.model.RescueTeam;
import it.unibo.drescue.model.RescueTeamImpl;

public class CPCoordinationMessage extends AbstractRoutingMessage {

    public final static String COORDINATION_MESSAGE = "coordination_message";

    private RescueTeamImpl rescueTeam;

    public CPCoordinationMessage() {
        super(COORDINATION_MESSAGE);
    }

    public RescueTeam getRescueTeam() {
        return this.rescueTeam;
    }

    public void setRescueTeam(final RescueTeamImpl rescueTeam) {
        this.rescueTeam = rescueTeam;
    }

}
