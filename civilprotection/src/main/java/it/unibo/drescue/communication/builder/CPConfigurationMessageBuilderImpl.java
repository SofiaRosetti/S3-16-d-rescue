package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.CPConfigurationMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;

import java.util.List;

public class CPConfigurationMessageBuilderImpl implements CPConfigurationMessageBuilder {

    private final CPConfigurationMessage message;

    public CPConfigurationMessageBuilderImpl() {
        this.message = new CPConfigurationMessage();
    }

    @Override
    public CPConfigurationMessageBuilder setFrom(final String from) {
        this.message.setFrom(from);
        return this;
    }

    @Override
    public CPConfigurationMessageBuilder setTo(final String to) {
        this.message.setTo(to);
        return this;
    }

    @Override
    public CPConfigurationMessageBuilder setRescueTeamCollection(final List<RescueTeamImpl> rescueTeamCollection) {
        this.message.setRescueTeamCollection(rescueTeamCollection);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
