package it.unibo.drescue.utils;

import it.unibo.drescue.communication.messages.ReplyCoordinationMessage;
import it.unibo.drescue.connection.RabbitMQ;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 *
 *
 *  It is a singleton that keep the process' state in order to ensure communication and coordination with other civil protection
 *
 *  The process can be in one critical section  at a time (the coordinator keep the state about one critical section)
 */
public class CoordinatorImpl implements Coordinator {

    private static CoordinatorImpl instance;
    private CoordinatorCondition condition;
    private Timestamp reqTimestamp = null;
    private String exchangeName = "";
    private String myID = "";
    private String cs = "";
    private Map<String, Boolean> pendingCPReplay;
    private Set<String> blockedCP;
    private RabbitMQ connection;


    private CoordinatorImpl() {
        condition = CoordinatorCondition.DETACHED;
    }

    public static CoordinatorImpl getInstance(){
        if (instance == null){
            instance = new CoordinatorImpl();
        }
        return instance;
    }

    @Override
    public RabbitMQ getConnection() {
        return this.connection;
    }

    @Override
    public void setConnection(RabbitMQ rabbitMQ) {
        this.connection = rabbitMQ;
    }

    @Override
    public CoordinatorCondition getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(CoordinatorCondition condition) {
        System.out.println("Condition " + this.condition + " --> " + condition);
        this.condition = condition;
    }

    @Override
    public Timestamp getReqTimestamp() {
        return this.reqTimestamp;
    }

    @Override
    public void setReqTimestamp(Timestamp reqTimestamp) {
        this.reqTimestamp = reqTimestamp;
    }

    @Override
    public String getMyID() {
        return this.myID;
    }

    @Override
    public void setMyID(String ID) {
        this.myID = ID;
    }

    @Override
    public String getCsName() {
        return this.cs;
    }

    @Override
    public void setCsName(String csName) {
        this.cs = csName;
    }

    @Override
    public void createPendingCivilProtectionReplayStructure(List<String> civilProtectionIDs) {
        if (this.pendingCPReplay == null) {
            this.pendingCPReplay = new HashMap<>();
        }
        for (String s: civilProtectionIDs) {
            this.pendingCPReplay.put(s, false);
        }
    }

    @Override
    public void updatePendingCivilProtectionReplayStructure(String civilProtectionID) {
        boolean allReplay = true;
        if (this.pendingCPReplay.get(civilProtectionID)!= null){
            this.pendingCPReplay.put(civilProtectionID, true);
        }
        for (String s: pendingCPReplay.keySet()) {
            if (pendingCPReplay.get(s)== false){
                allReplay = false;
            }
        }
        if (allReplay == true){
            this.condition = CoordinatorCondition.HELD;
            System.out.println("change condition to HELD");
        }
        System.out.println("pendingCPReplay: "+ this.pendingCPReplay);
    }


    @Override
    public void addBlockedCP(String cpID) {
        if (this.blockedCP == null){
            this.blockedCP = new HashSet<>();
        }
        this.blockedCP.add(cpID);
        System.out.println("blocked CP: "+ this.blockedCP);
    }


    @Override
    public void sendReplayMessageTo(String csName, String to, RescueTeamCondition rescueTeamCondition) {
        ReplyCoordinationMessage replyCoordinationMessage = new ReplyCoordinationMessage();
        replyCoordinationMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
        replyCoordinationMessage.setRescueTeamID(csName);
        replyCoordinationMessage.setRTcondition(rescueTeamCondition);
        replyCoordinationMessage.setFrom(myID);
        replyCoordinationMessage.setTo(to);
        try {
            this.connection.sendMessage(this.exchangeName, csName, null, replyCoordinationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backToCs(RescueTeamCondition rescueTeamCondition) {
        this.condition = CoordinatorCondition.DETACHED;
        this.reqTimestamp = null;
        this.pendingCPReplay = null;
        if (this.blockedCP != null){
            for (String s: this.blockedCP){
                sendReplayMessageTo(cs, s, rescueTeamCondition );
            }
        }
        this.cs = "";
    }

    @Override
    public void setExchange(String exchange) {
        this.exchangeName = exchange;
    }
}
