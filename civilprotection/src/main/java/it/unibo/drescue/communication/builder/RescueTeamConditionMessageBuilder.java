package it.unibo.drescue.communication.builder;


import it.unibo.drescue.utils.RescueTeamCondition;

public interface RescueTeamConditionMessageBuilder extends RoutingMessageBuilder {

    RescueTeamConditionMessageBuilder setRescueTeamID(String rescueTeamID);

    RescueTeamConditionMessageBuilder setRescueTeamCondition(RescueTeamCondition rescueTeamCondtion);
}
