package it.unibo.drescue.communication.builder;

import it.unibo.drescue.utils.RescueTeamCondition;

public interface ReplyRescueTeamConditionMessageBuilder extends RoutingMessageBuilder {

    ReplyRescueTeamConditionMessageBuilder setRescueTeamID(String rescueTeamID);

    ReplyRescueTeamConditionMessageBuilder setRescueTeamCondition(RescueTeamCondition rescueTeamCondtion);
}
