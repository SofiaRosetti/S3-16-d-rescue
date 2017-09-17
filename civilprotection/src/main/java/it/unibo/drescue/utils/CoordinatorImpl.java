package it.unibo.drescue.utils;

import it.unibo.drescue.communication.messages.ReplayCoordinationMessage;
import it.unibo.drescue.connection.RabbitMQ;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 *  Una Cs per RescueTeam
 *  Ogni CP accede ad una cs alla volta (il coordinator mantiene le informazioni di 1 sola cs)
 */
public class CoordinatorImpl implements Coordinator {

    public final static String EXCHANGE_NAME = "rabbit_cp";

    private static CoordinatorImpl instance;
    private CoordinatorCondition condition;
    private Timestamp reqTimestamp = null;
    private String myID = "";
    private String cs = "";
    private Map<String, Boolean> pendingCPReplay; //Le Risp che devo ricevere prima di accedere alla Cs
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
    public void setConnection(RabbitMQ rabbitMQ) {
        this.connection = rabbitMQ;
    }

    @Override
    public RabbitMQ getConnection() {
        return this.connection;
    }

    @Override
    public void setCondition(CoordinatorCondition condition) {
        //TODO quando setto lo stato a WANTED impostare anche il timestamp
        System.out.println("Condition " + this.condition + " --> " + condition);
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
    public void setMyID(String ID) {
        this.myID = ID;
    }

    @Override
    public String getMyID() {
        return this.myID;
    }

    @Override
    public void setCsName(String csName) {
        this.cs = csName;
    }

    @Override
    public String getCsName() {
        return this.cs;
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
        boolean ok = true;
        if (this.pendingCPReplay.get(civilProtectionID)!= null){
            this.pendingCPReplay.put(civilProtectionID, true);
        }
        for (String s: pendingCPReplay.keySet()) {
            if (pendingCPReplay.get(s)== false){
                ok = false;
            }
        }
        if (ok == true){
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
    public void sendReplayMessage(String csName) {
        ReplayCoordinationMessage replayCoordinationMessage = new ReplayCoordinationMessage();
        replayCoordinationMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
        replayCoordinationMessage.setFrom(myID);

        try {
            this.connection.sendMessage(EXCHANGE_NAME, csName, null, replayCoordinationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendReplayMessageTo(String csName, String to) {
        ReplayCoordinationMessage replayCoordinationMessage = new ReplayCoordinationMessage();
        replayCoordinationMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
        replayCoordinationMessage.setRescueTeamID(csName);
        replayCoordinationMessage.setRTcondition(RescueTeamCondition.AVAILABLE);
        replayCoordinationMessage.setFrom(myID);
        replayCoordinationMessage.setTo(to);
        try {
            this.connection.sendMessage(EXCHANGE_NAME, csName, null, replayCoordinationMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backToCs() {
        this.condition = CoordinatorCondition.DETACHED;
        this.reqTimestamp = null;
        this.pendingCPReplay = null;
        if (this.blockedCP != null){
            for (String s: this.blockedCP){
                sendReplayMessageTo(cs, s);
            }
        }
        this.cs = "";
    }
}
