package it.unibo.drescue.communication.builder;

import it.unibo.drescue.model.RescueTeamImpl;
import java.util.List;

/**
 *
 */
public interface CPConfigurationMessageBuilder extends RoutingMessageBuilder{

    /**
     *
     * @param rescueTeamCollection
     * @return
     */
    CPConfigurationMessageBuilder setRescueTeamCollection(List<RescueTeamImpl> rescueTeamCollection);
}
