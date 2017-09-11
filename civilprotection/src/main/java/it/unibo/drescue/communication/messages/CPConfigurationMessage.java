package it.unibo.drescue.communication.messages;

import it.unibo.drescue.model.RescueTeamImpl;

import java.util.List;

public class CPConfigurationMessage extends AbstractRoutingMessage {

    private List<RescueTeamImpl> rescueTeamCollection;

    public CPConfigurationMessage() {
        super(MessageType.CONFIGURATION_MESSAGE);
    }

    public List<RescueTeamImpl> getRescueTeamCollection() {
        return this.rescueTeamCollection;
    }

    public void setRescueTeamCollection(final List<RescueTeamImpl> rescueTeamCollection) {
        this.rescueTeamCollection = rescueTeamCollection;
    }

}
