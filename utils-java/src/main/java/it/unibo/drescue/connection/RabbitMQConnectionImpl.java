package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Class that represents a connection.
 */
public class RabbitMQConnectionImpl implements RabbitMQConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConnection.class);

    private final String host;
    private Connection connection;

    /**
     * Create an object with the option to open a connection to the given host.
     *
     * @param host host name
     */
    public RabbitMQConnectionImpl(final String host) {
        this.host = host;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void openConnection() {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        try {
            this.connection = factory.newConnection();
        } catch (final IOException e) {
            LOGGER.error("Error occur during an operation of I/O.", e);
        } catch (final TimeoutException e) {
            LOGGER.error("Error occur while a blocking operation times out.", e);
        }
    }

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (final IOException e) {
            LOGGER.error("Error occur during an operation of I/O.", e);
        }
    }
}
