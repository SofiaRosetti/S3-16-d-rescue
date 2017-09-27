package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.Alert;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface of DAO for Alert objects, it extends UpdatableDao
 * and specify some other useful methods for this objectModel
 */
public interface AlertDao extends UpdatableDao {

    /**
     * Get last inserted x alerts in the given district
     *
     * @param x          number of alarms to return
     * @param districtId district identifier
     * @return a list with last inserted x alerts
     * @throws DBQueryException if something goes wrong executing the findLast query
     */
    List<Alert> findLast(int x, String districtId) throws DBQueryException;

    /**
     * Map current timestamp into a valid DB format
     *
     * @return current timestamp in a valid format
     */
    Timestamp getCurrentTimestampForDb();
}
