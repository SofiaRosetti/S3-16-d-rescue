package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnectionImpl;

public class DBInitializationStart {

    private static final String FILE_PATH = "server/res";

    private static final String DISTRICTS_FILE = "/districts.json";
    private static final String EVENT_TYPES_FILE = "/event_types.json";
    private static final String CIVIL_PROTECTION_FILE = "/civil_protections.json";
    private static final String CP_AREAS_FILE = "/cp_areas.json";

    /**
     * Used to initialize DB with static tables contents
     */
    public static void main(final String[] args) {
        start();
    }

    public static void start() {
        final DBInitialization dbInitialization =
                new DBInitializationImpl(DBConnectionImpl.getLocalConnection());

        dbInitialization.insertAllDistrictsFrom(FILE_PATH + DISTRICTS_FILE);

        dbInitialization.insertAllEventTypesFrom(FILE_PATH + EVENT_TYPES_FILE);

        dbInitialization.insertAllCivilProtectionsFrom(FILE_PATH + CIVIL_PROTECTION_FILE);

        dbInitialization.insertAllCpAreasFrom(FILE_PATH + CP_AREAS_FILE);
    }
}
