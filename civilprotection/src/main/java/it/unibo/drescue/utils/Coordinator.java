package it.unibo.drescue.utils;

import java.sql.Timestamp;

public interface Coordinator {

    /**
     *
     * @return
     */
    Coordinator getIstance();

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
     * @param timestamp
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
