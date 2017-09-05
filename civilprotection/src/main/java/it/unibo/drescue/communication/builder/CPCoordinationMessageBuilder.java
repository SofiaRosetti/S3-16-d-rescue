package it.unibo.drescue.communication.builder;

import it.unibo.drescue.model.RescueTeam;
import it.unibo.drescue.model.RescueTeamImpl;

public interface CPCoordinationMessageBuilder extends RoutingMessageBuilder{

    /**
     *
     * @param rescueTeam
     * @return
     */
    CPCoordinationMessageBuilder setRescueTeam(final RescueTeamImpl rescueTeam);
}
