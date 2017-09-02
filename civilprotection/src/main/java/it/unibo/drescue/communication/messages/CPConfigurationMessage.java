package it.unibo.drescue.communication.messages;

import it.unibo.drescue.model.RescueTeamImpl;

import java.util.List;

public class CPConfigurationMessage extends AbstractRoutingMessage {

    public final static String CONFIGURATION_MESSAGE = "configuration_message";

    private String messageType;
    private List<RescueTeamImpl> rescueTeamCollection;

    public List<RescueTeamImpl> getRescueTeamCollection() {

        return rescueTeamCollection;
    }

    public void setRescueTeamCollection(List<RescueTeamImpl> rescueTeamCollection) {
        this.rescueTeamCollection = rescueTeamCollection;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType() {
        this.messageType = CONFIGURATION_MESSAGE;
    }
}
