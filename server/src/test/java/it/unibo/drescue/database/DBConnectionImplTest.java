package it.unibo.drescue.database;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DBConnectionImplTest {

    private DBConnectionImpl dbConnection;

    @Test
    public void isOpeningAndClosingLocalConnection() {
        this.testConnectionWithAddress(
                DBConnectionImpl.getLocalConnection(),
                DBConnectionImpl.LOCAL_ADDRESS);
    }

    @Test
    public void isOpeningAndClosingRemoteConnection() {
        this.testConnectionWithAddress(
                DBConnectionImpl.getRemoteConnection(),
                DBConnectionImpl.REMOTE_ADDRESS);
    }

    private void testConnectionWithAddress(
            final DBConnectionImpl connection, final String address) {
        this.dbConnection = connection;
        this.dbConnection.openConnection();
        assertTrue(this.dbConnection.isValid());
        assertTrue(this.dbConnection.getDbAddress().equals(address));
        this.dbConnection.closeConnection();
        assertFalse(this.dbConnection.isValid());
    }

}