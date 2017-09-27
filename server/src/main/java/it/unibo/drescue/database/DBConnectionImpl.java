package it.unibo.drescue.database;

import it.unibo.drescue.database.dao.*;
import it.unibo.drescue.database.exceptions.DBConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implementation class that allows communication with database.
 */
public class DBConnectionImpl implements DBConnection {

    protected static final String LOCAL_ADDRESS = "jdbc:mysql://localhost:3306/drescueDB";
    protected static final String REMOTE_ADDRESS =
            "jdbc:mysql://rds-mysql-drescue.cnwnbp8hx7vq.us-east-2.rds.amazonaws.com:3306/drescueDB";
    private static final String DRIVER_NAME =
            "com.mysql.jdbc.Driver";

    private static final String OPEN_EXCEPTION = "Exception while trying to open connection";
    private static final String CLOSE_EXCEPTION = "Exception while trying to close connection";
    private static final String TABLE_EXCEPTION = "Exception while trying to link to a nonexistent table";

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnectionImpl.class);

    private static Connection connection;
    private static String dbAddress;
    private static String dbUsername;
    private static String dbPassword;

    /**
     * Private constructor, cannot be instantiated
     */
    private DBConnectionImpl() {
    }

    /**
     * Instantiate a new connection to db in local environment
     *
     * @return a db connection in local
     */
    public static DBConnectionImpl getLocalConnection() {
        setEnvironment(Environment.LOCAL);
        return new DBConnectionImpl();
    }

    /**
     * Instantiate a new connection to db in remote environment
     *
     * @return a db connection in remote
     */
    public static DBConnectionImpl getRemoteConnection() {
        setEnvironment(Environment.REMOTE);
        return new DBConnectionImpl();
    }

    private static void setEnvironment(final Environment env) {
        switch (env) {
            case LOCAL:
                dbAddress = LOCAL_ADDRESS;
                dbUsername = "admin";
                dbPassword = "4dm1n";
                break;
            case REMOTE:
                dbAddress = REMOTE_ADDRESS;
                dbUsername = "masterDrescue";
                dbPassword = "rdsTeamPass";
                break;
        }
    }

    @Override
    public void openConnection() throws DBConnectionException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
                LOGGER.info("Connection established with db address: " + dbAddress);
            }
        } catch (final Exception e) {
            throw new DBConnectionException(OPEN_EXCEPTION);
        }
    }

    @Override
    public void closeConnection() throws DBConnectionException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Connection closed");
            }
        } catch (final Exception e) {
            throw new DBConnectionException(CLOSE_EXCEPTION);
        }
    }

    @Override
    public boolean isValid() {
        try {
            return connection.isValid(5000);
        } catch (final SQLException e) {
            return false;
        }
    }

    protected String getDbAddress() {
        return dbAddress;
    }

    @Override
    public GenericDaoAbstract getDAO(final Table table) throws DBConnectionException {

        try {
            if (connection == null || connection.isClosed()) { //Ensure that connection is open
                this.openConnection();
            }
        } catch (final Exception e) {
            throw new DBConnectionException(OPEN_EXCEPTION);
        }

        switch (table) {
            case USER:
                return new UserDaoImpl(connection);
            case DISTRICT:
                return new DistrictDaoImpl(connection);
            case EVENT_TYPE:
                return new EventTypeDaoImpl(connection);
            case ALERT:
                return new AlertDaoImpl(connection);
            case CIVIL_PROTECTION:
                return new CivilProtectionDaoImpl(connection);
            case CP_AREA:
                return new CpAreaDaoImpl(connection);
            case UPVOTED_ALERT:
                return new UpvotedAlertDaoImpl(connection);
            case RESCUE_TEAM:
                return new RescueTeamDaoImpl(connection);
            case CP_ENROLLMENT:
                return new CpEnrollmentDaoImpl(connection);
            default:
                throw new DBConnectionException(TABLE_EXCEPTION);
        }

    }

    private enum Environment {
        LOCAL,
        REMOTE
    }
}
