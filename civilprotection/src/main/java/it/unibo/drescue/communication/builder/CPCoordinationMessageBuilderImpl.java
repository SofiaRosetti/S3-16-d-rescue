package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.CPCoordinationMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;

public class CPCoordinationMessageBuilderImpl implements CPCoordinationMessageBuilder{

    private CPCoordinationMessage message;

    public CPCoordinationMessageBuilderImpl() {
        this.message = new CPCoordinationMessage();
    }

    @Override
    public CPCoordinationMessageBuilder setFrom(final String from) {
        message.setFrom(from);
        return this;
    }

    @Override
    public CPCoordinationMessageBuilder setTo(final String to) {
        message.setTo(to);
        return this;
    }

    @Override
    public CPCoordinationMessageBuilder setMessageType() {
        message.setMessageType();
        return this;
    }

    @Override
    public CPCoordinationMessageBuilder setRescueTeam(final RescueTeamImpl rescueTeam) {
        message.setRescueTeam(rescueTeam);
        return this;
    }

    @Override
    public Message build() {
        return message;
    }
}
