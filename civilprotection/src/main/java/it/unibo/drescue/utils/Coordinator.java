package it.unibo.drescue.utils;

import java.sql.Timestamp;

public interface Coordinator {


    /**
     *
     * @param condition
     */
    void setCondition(CoordinatorCondition condition);

    /**
     *
     * @return
     */
    CoordinatorCondition getCondition();

    /**
     *
     * @param reqTimestamp
     */
    void setReqTimestamp(Timestamp reqTimestamp);

    /**
     *
     * @return
     */
    Timestamp getReqTimestamp();

    /**
     *
     * @param civilProtectionIDs
     */
    void createPendingCivilProtectionReplayStructure(String[] civilProtectionIDs);

    /**
     *
     * @param rescueTeamID
     */
    void addReferRescueTeam(String rescueTeamID);

}
