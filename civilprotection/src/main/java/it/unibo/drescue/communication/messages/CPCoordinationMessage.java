package it.unibo.drescue.communication.messages;

import it.unibo.drescue.model.RescueTeam;
import it.unibo.drescue.model.RescueTeamImpl;
import it.unibo.drescue.utils.RescueTeamCondition;

public class CPCoordinationMessage extends AbstractRoutingMessage {

    private RescueTeamImpl rescueTeam;


    public CPCoordinationMessage() {
        super(MessageType.COORDINATION_MESSAGE);
    }

    public RescueTeam getRescueTeam() {
        return this.rescueTeam;
    }

    public void setRescueTeam(final RescueTeamImpl rescueTeam) {
        this.rescueTeam = rescueTeam;
    }



}
