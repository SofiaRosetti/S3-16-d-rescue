package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.CPCoordinationMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;

public class CPCoordinationMessageBuilderImpl implements CPCoordinationMessageBuilder {

    private final CPCoordinationMessage message;

    public CPCoordinationMessageBuilderImpl() {
        this.message = new CPCoordinationMessage();
    }

    @Override
    public CPCoordinationMessageBuilder setFrom(final String from) {
        this.message.setFrom(from);
        return this;
    }

    @Override
    public CPCoordinationMessageBuilder setTo(final String to) {
        this.message.setTo(to);
        return this;
    }

    @Override
    public CPCoordinationMessageBuilder setRescueTeam(final RescueTeamImpl rescueTeam) {
        this.message.setRescueTeam(rescueTeam);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
