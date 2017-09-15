package it.unibo.drescue.utils;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

public class CoordinatorImpl implements Coordinator {

    private static CoordinatorImpl instance;
    private CoordinatorCondition condition;
    private Timestamp reqTimestamp;
    private String ID;
    private Map<String, Boolean> pendingCPReplay;
    private Set<String> referRescueTeam;

    private CoordinatorImpl() { }

    public static Coordinator getInstance()
    {
        if (instance == null){
            instance = new CoordinatorImpl();
        }
        return instance;
    }
}
