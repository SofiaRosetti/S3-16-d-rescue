package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnectionImpl;

public class DBInitializationStart {

    private static final String FILE_PATH = "server/res";

    private static final String DISTRICTS_FILE = "/districts.json";
    private static final String EVENT_TYPES_FILE = "/event_types.json";

    /**
     * Used to initialize DB with static tables contents
     */
    public static void main(final String[] args) {
        final DBInitialization dbInitialization =
                new DBInitializationImpl(DBConnectionImpl.getLocalConnection());

        dbInitialization.insertAllDistrictsFrom(FILE_PATH + DISTRICTS_FILE);

        dbInitialization.insertAllEventTypesFrom(FILE_PATH + EVENT_TYPES_FILE);

        //TODO insert all Civil_Protections

    }
}
