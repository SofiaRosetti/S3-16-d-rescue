package it.unibo.drescue.utils;

import com.google.appengine.repackaged.com.google.api.client.util.ArrayMap;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoordinatorImpl implements Coordinator {

    private static Coordinator istance = null;
    private CoordinatorCondition condition;
    private Timestamp reqTimestamp;
    private String ID;
    private Map<String, Boolean> pendingCPReplay;
    private Set<String> referRescueTeam;

    private CoordinatorImpl() {
        this.condition = CoordinatorCondition.DETACHED;
        this.reqTimestamp = null;
        this.ID = "";
        this.pendingCPReplay = new ArrayMap<>();
        this.referRescueTeam = new HashSet<>();
    }

    @Override
    public Coordinator getIstance(){
        if (istance == null){
            istance = new CoordinatorImpl();
        }
        return istance;
    }

    @Override
    public void setCondition(CoordinatorCondition condition) {
        this.condition = condition;
    }

    @Override
    public CoordinatorCondition getCondition() {
        return this.condition;
    }

    @Override
    public void setReqTimestamp(Timestamp reqTimestamp) {
        this.reqTimestamp = reqTimestamp;
    }

    @Override
    public Timestamp getReqTimestamp() {
        return this.reqTimestamp;
    }

    @Override
    public void createPendingCivilProtectionReplayStructure(String[] civilProtectionIDs) {
       for (String cpID: civilProtectionIDs){
           this.pendingCPReplay.put(cpID, false);
       }
    }

    @Override
    public void addReferRescueTeam(String rescueTeamID) {
        this.referRescueTeam.add(rescueTeamID);
    }
}
