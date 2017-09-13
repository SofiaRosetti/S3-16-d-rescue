package it.unibo.drescue.utils;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

public class CoordinatorImpl implements Coordinator {

    private CoordinatorCondition condition;
    private Timestamp reqTimestamp;
    private String ID;
    private Map<String, Boolean> pendingCPReplay;
    private Set<String> referRescueTeam;

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
    public void createPendingCivilProtectionReplayStructure(String[] civilProtectionIDs) {

    }

    @Override
    public void addReferRescueTeam(String rescueTeamID) {

    }
}
