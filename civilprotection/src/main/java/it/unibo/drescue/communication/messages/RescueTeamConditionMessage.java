package it.unibo.drescue.communication.messages;

import it.unibo.drescue.utils.RescueTeamCondition;

public class RescueTeamConditionMessage extends AbstractRoutingMessage {

    private String rescueTeamID;
    private RescueTeamCondition rescueTeamCondition;

    public RescueTeamConditionMessage() {
       super(MessageType.RESCUE_TEAM_CONDITION);
    }

    public String getRescueTeamID() {
        return rescueTeamID;
    }

    public void setRescueTeamID(String rescueTeamID) {
        this.rescueTeamID = rescueTeamID;
    }

    public RescueTeamCondition getRescueTeamCondition() {
        return rescueTeamCondition;
    }

    public void setRescueTeamCondition(RescueTeamCondition rescueTeamCondition) {
        this.rescueTeamCondition = rescueTeamCondition;
    }
}
