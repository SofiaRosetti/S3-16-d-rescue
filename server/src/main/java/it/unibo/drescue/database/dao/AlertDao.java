package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.Alert;

import java.util.List;

public interface AlertDao extends UpdatableDao {

    /**
     * Get last inserted x alerts
     *
     * @return a list with last inserted x alerts
     */
    List<Alert> findLast(int x);
}
