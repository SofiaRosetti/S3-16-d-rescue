package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.CPConfigurationMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.RescueTeamImpl;
import java.util.List;

public class CPConfigurationMessageBuilderImpl implements CPConfigurationMessageBuilder {

    private CPConfigurationMessage message;

    public CPConfigurationMessageBuilderImpl() {
        this.message = new CPConfigurationMessage();
    }

    @Override
    public CPConfigurationMessageBuilder setFrom(String from) {
        message.setFrom(from);
        return this;
    }

    @Override
    public CPConfigurationMessageBuilder setTo(String to) {
        message.setTo(to);
        return this;
    }

    @Override
    public CPConfigurationMessageBuilder setMessageType() {
        message.setMessageType();
        return this;
    }

    @Override
    public CPConfigurationMessageBuilder setRescueTeamCollection(List<RescueTeamImpl> rescueTeamCollection) {
        message.setRescueTeamCollection(rescueTeamCollection);
        return this;
    }

    @Override
    public Message build() {
        return message;
    }
}
