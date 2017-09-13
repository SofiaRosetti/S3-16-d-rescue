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
    void setReqTimestamp(Timestamp timestamp);

    /**
     *
     * @return
     */
    Timestamp getReqTimestamp();

    /**
     *
     * @param civilProtectionIDs
     */
    void createPendingCivilPretectionReplayStructure(String[] civilProtectionIDs);

    /**
     *
     * @param rescueTeamID
     */
    void addReferRescueTeam(String rescueTeamID);

}
