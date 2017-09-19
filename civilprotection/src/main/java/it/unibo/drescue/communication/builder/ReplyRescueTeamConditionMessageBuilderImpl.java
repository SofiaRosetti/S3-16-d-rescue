package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.ReplyRescueTeamConditionMessage;
import it.unibo.drescue.utils.RescueTeamCondition;

public class ReplyRescueTeamConditionMessageBuilderImpl implements ReplyRescueTeamConditionMessageBuilder {

    private final ReplyRescueTeamConditionMessage message;

    public ReplyRescueTeamConditionMessageBuilderImpl() {
        this.message = new ReplyRescueTeamConditionMessage();
    }

    @Override
    public ReplyRescueTeamConditionMessageBuilder setRescueTeamID(String rescueTeamID) {
        this.message.setRescueTeamID(rescueTeamID);
        return this;
    }

    @Override
    public ReplyRescueTeamConditionMessageBuilder setRescueTeamCondition(RescueTeamCondition rescueTeamCondtion) {
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
