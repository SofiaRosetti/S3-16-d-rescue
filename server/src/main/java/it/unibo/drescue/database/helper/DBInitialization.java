package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnection;

/**
 * Interface that allows to initialize records of database with data containing in an external file.
 */
public interface DBInitialization {

    /**
     * Insert into DB all objects listed in a json file
     *
     * @param table    in which table it has to be inserted
     * @param pathFile indicates the path where the file with objects is located
     */
    void insertAllObjectsFromAFile(DBConnection.Table table, String pathFile);

}
