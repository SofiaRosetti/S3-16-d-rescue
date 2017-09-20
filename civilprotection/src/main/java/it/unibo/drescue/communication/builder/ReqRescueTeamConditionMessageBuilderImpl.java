package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.ReqRescueTeamConditionMessage;

public class ReqRescueTeamConditionMessageBuilderImpl implements ReqRescueTeamConditionMessageBuilder {

    private ReqRescueTeamConditionMessage message;

    public ReqRescueTeamConditionMessageBuilderImpl() {
        this.message = new ReqRescueTeamConditionMessage();
    }

    @Override
    public ReqRescueTeamConditionMessageBuilder setRescueTeamID(String rescueTeamID) {
        this.message.setRescueTeamID(rescueTeamID);
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
