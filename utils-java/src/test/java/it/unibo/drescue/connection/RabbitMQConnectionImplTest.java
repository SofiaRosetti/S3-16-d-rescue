package it.unibo.drescue.connection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RabbitMQConnectionImplTest {

    private RabbitMQConnectionImpl serverConnection;

    @Before
    public void createConnection() {
        this.serverConnection = new RabbitMQConnectionImpl("localhost");
        this.serverConnection.openConnection();
    }

    @Test
    public void isConnectionOpen() {
        assertTrue(this.serverConnection.getConnection().isOpen());
        this.serverConnection.closeConnection();
    }

    @Test
    public void isConnectionClosed() {
        this.serverConnection.closeConnection();
        assertFalse(this.serverConnection.getConnection().isOpen());
    }
}
