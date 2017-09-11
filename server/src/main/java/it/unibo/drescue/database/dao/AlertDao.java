package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.Alert;

import java.sql.Timestamp;
import java.util.List;

public interface AlertDao extends UpdatableDao {

    /**
     * Get last inserted x alerts in the given district
     *
     * @param x
     * @param districtId
     * @return a list with last inserted x alerts
     */
    List<Alert> findLast(int x, String districtId);

    /**
     * Map current timestamp into a valid DB format
     *
     * @return current timestamp in a valid format
     */
    Timestamp getCurrentTimestampForDb();
}
