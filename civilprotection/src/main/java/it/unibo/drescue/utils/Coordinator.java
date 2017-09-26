package it.unibo.drescue.utils;

import it.unibo.drescue.connection.RabbitMQ;

import java.sql.Timestamp;
import java.util.List;

/**
 * The coordinator interface for civil protections
 */
public interface Coordinator {

    /**
     * @return the connection
     */
    RabbitMQ getConnection();

    /**
     * Set connection to send replay message
     *
     * @param rabbitMQ the channel
     */
    void setConnection(RabbitMQ rabbitMQ);

    /**
     * @return the process condition
     */
    CoordinatorCondition getCondition();

    /**
     * @param condition the process condition
     */
    void setCondition(CoordinatorCondition condition);

    /**
     * @return the timestamp of critical section request
     */
    Timestamp getReqTimestamp();

    /**
     * @param reqTimestamp the timestamp of critical section request
     */
    void setReqTimestamp(Timestamp reqTimestamp);

    /**
     * @return the precess ID
     */
    String getMyID();

    /**
     * @param ID the precess ID
     */
    void setMyID(String ID);

    /**
     * @return the name of critical section in which the process wants to enter
     */
    String getCsName();

    /**
     * @param csName the name of critical section in which the process wants to enter
     */
    void setCsName(String csName);

    /**
     * @param civilProtectionIDs the ID of civil protection
     */
    void createPendingCivilProtectionReplayStructure(List<String> civilProtectionIDs);

    /**
     * Create a structure to persist the other civil protection replay in the face of process request
     *
     * @param civilProtectionID the list ID of the civil protection  from which the process expect an replay message
     */
    void updatePendingCivilProtectionReplayStructure(String civilProtectionID);

    /**
     * @param cpID the id of civil protection that are blocked until the process came back from critical section
     */
    void addBlockedCP(String cpID);

    /**
     * @param csName              the critical section name
     * @param to                  the process to which send a replay
     * @param rescueTeamCondition the rescue team condition
     */
    void sendReplayMessageTo(String csName, String to, RescueTeamCondition rescueTeamCondition);

    /**
     * The process came back to critical section
     *
     * @param rescueTeamCondition the new rescue team's condition update into critical section
     */
    void backToCs(RescueTeamCondition rescueTeamCondition);

    /**
     * Set the name of exchange
     *
     * @param exchange the exchange's name
     */
    void setExchange(String exchange);

}
