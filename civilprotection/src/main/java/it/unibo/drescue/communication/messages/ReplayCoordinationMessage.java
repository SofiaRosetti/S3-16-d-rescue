package it.unibo.drescue.communication.messages;

import it.unibo.drescue.utils.RescueTeamCondition;

public class ReplayCoordinationMessage extends CoordinationMessage {

    private RescueTeamCondition condition;

    /**
     *
     */
    public ReplayCoordinationMessage() {
        super(MessageType.REPLAY_COORDINATION_MESSAGE);
    }

    public void setRTcondition(RescueTeamCondition condition){
        this.condition = condition;
    }

    public RescueTeamCondition getRTCondition(){
        return this.condition;
    }


}
