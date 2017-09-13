package it.unibo.drescue.utils;

import java.sql.Timestamp;

public class CoordinatorImpl implements Coordinator {
    @Override
    public Coordinator getIstance() {
        return null;
    }

    @Override
    public void setCondition(CoordinatorCondition condition) {

    }

    @Override
    public CoordinatorCondition getCondition() {
        return null;
    }

    @Override
    public void setReqTimestamp(Timestamp timestamp) {

    }

    @Override
    public Timestamp getReqTimestamp() {
        return null;
    }

    @Override
    public void createPendingCivilPretectionReplayStructure(String[] civilProtectionIDs) {

    }

    @Override
    public void addReferRescueTeam(String rescueTeamID) {

    }
}
