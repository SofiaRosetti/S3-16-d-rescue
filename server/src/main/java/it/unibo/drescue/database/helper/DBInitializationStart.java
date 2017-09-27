package it.unibo.drescue.database.helper;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;

/**
 * Main of database initialization.
 * Initialize records for districts, event types, civil protection and civil protection areas.
 */
public class DBInitializationStart {

    private static final String FILE_PATH = "server/res";

    private static final String DISTRICTS_FILE = "/districts.json";
    private static final String EVENT_TYPES_FILE = "/event_types.json";
    private static final String CIVIL_PROTECTION_FILE = "/civil_protections.json";
    private static final String CP_AREAS_FILE = "/cp_areas.json";
    private static final String USERS_FILE = "/users.json";
    private static final String RESCUE_TEAMS_FILE = "/rescue_teams.json";
    private static final String CP_ENROLLMENT_FILE = "/cp_enrollments.json";

    /**
     * Used to initialize DB with static tables contents.
     *
     * @param args some arguments
     */
    public static void main(final String[] args) {
        start();
    }

    public static void start() {
        final DBInitialization dbInitialization =
                new DBInitializationImpl(DBConnectionImpl.getLocalConnection());

        //Initialize districts
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.DISTRICT,
                FILE_PATH + DISTRICTS_FILE);

        //Initialize event_types
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.EVENT_TYPE,
                FILE_PATH + EVENT_TYPES_FILE);

        //Initialize civil_protections
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.CIVIL_PROTECTION,
                FILE_PATH + CIVIL_PROTECTION_FILE);

        //Initialize cp_areas
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.CP_AREA,
                FILE_PATH + CP_AREAS_FILE);

        //Initialize users
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.USER,
                FILE_PATH + USERS_FILE);

        //Initialize rescue_teams
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.RESCUE_TEAM,
                FILE_PATH + RESCUE_TEAMS_FILE);

        //Initialize cp_enrollments
        dbInitialization.insertAllObjectsFromAFile(
                DBConnection.Table.CP_ENROLLMENT,
                FILE_PATH + CP_ENROLLMENT_FILE);
    }
}
