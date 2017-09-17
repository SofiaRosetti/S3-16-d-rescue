package it.unibo.drescue.utils;

import it.unibo.drescue.connection.RabbitMQ;

import java.sql.Timestamp;
import java.util.List;

public interface Coordinator {

    /**
     *
     * @param rabbitMQ
     */
    void setConnection (RabbitMQ rabbitMQ);

    /**
     *
     * @return
     */
    RabbitMQ getConnection();

    /**
     *
     * @param condition the process condition
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
     * @param ID
     */
    void setMyID(String ID);

    /**
     *
     * @return
     */
    String  getMyID();

    /**
     *
     * @param csName
     */
    void setCsName(String csName);

    /**
     *
     * @return
     */
    String getCsName();

    /**
     *
     * @param civilProtectionIDs
     */
    void createPendingCivilProtectionReplayStructure(List<String> civilProtectionIDs);

    /**
     *
     * @param civilProtectionID
     */
    void updatePendingCivilProtectionReplayStructure(String civilProtectionID);

    /**
     *
     * @param cpID
     */
    void addBlockedCP(String cpID);

    /**
     *
     * @param csName
     */
    void sendReplayMessage(String csName);

    /**
     *
     * @param csName
     * @param to
     */
    void sendReplayMessageTo(String csName, String to);

    /**
     *
     */
    void backToCs();

}
