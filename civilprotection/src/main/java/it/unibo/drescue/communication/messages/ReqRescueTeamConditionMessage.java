package it.unibo.drescue.communication.messages;

import it.unibo.drescue.utils.RescueTeamCondition;

public class ReqRescueTeamConditionMessage extends AbstractRoutingMessage {

    private String rescueTeamID;

    /**
     *
     */
    public ReqRescueTeamConditionMessage() {
        super(MessageType.REQ_RESCUE_TEAM_CONDITION);
    }

    public String getRescueTeamID() {
        return rescueTeamID;
    }

    public void setRescueTeamID(String rescueTeamID) {
        this.rescueTeamID = rescueTeamID;
    }



}
