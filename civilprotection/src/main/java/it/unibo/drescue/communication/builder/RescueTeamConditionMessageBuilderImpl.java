package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.RescueTeamConditionMessage;
import it.unibo.drescue.model.RescueTeamImplBuilder;
import it.unibo.drescue.utils.RescueTeamCondition;

public class RescueTeamConditionMessageBuilderImpl implements RescueTeamConditionMessageBuilder {

    private final RescueTeamConditionMessage message;

    public RescueTeamConditionMessageBuilderImpl() {
        this.message = new RescueTeamConditionMessage();
    }

    @Override
    public RescueTeamConditionMessageBuilder setRescueTeamID(String rescueTeamID) {
        this.message.setRescueTeamID(rescueTeamID);
        return this;
    }

    @Override
    public RescueTeamConditionMessageBuilder setRescueTeamCondition(RescueTeamCondition rescueTeamCondtion) {
        this.message.setRescueTeamCondition(rescueTeamCondtion);
        return this;
    }

    @Override
    public RoutingMessageBuilder setFrom(String from) {
        this.message.setFrom(from);
        return this;
    }

    @Override
    public RoutingMessageBuilder setTo(String to) {
        this.message.setTo(to);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
