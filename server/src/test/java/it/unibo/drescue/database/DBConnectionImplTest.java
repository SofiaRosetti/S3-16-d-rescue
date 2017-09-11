package it.unibo.drescue.database;

import it.unibo.drescue.database.exceptions.DBConnectionException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DBConnectionImplTest {

    private DBConnectionImpl dbConnection;

    /*@Test
    public void isOpeningAndClosingLocalConnection() throws DBConnectionException {
        this.testConnectionWithAddress(
                DBConnectionImpl.getLocalConnection(),
                DBConnectionImpl.LOCAL_ADDRESS);
    }*/

    @Test
    public void isOpeningAndClosingRemoteConnection() throws DBConnectionException {
        this.testConnectionWithAddress(
                DBConnectionImpl.getRemoteConnection(),
                DBConnectionImpl.REMOTE_ADDRESS);
    }

    private void testConnectionWithAddress(
            final DBConnectionImpl connection, final String address) throws DBConnectionException {
        this.dbConnection = connection;
        this.dbConnection.openConnection();
        assertTrue(this.dbConnection.isValid());
        assertTrue(this.dbConnection.getDbAddress().equals(address));
        this.dbConnection.closeConnection();
        assertFalse(this.dbConnection.isValid());
    }

}