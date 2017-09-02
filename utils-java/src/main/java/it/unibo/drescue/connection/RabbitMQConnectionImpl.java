package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Class that represents a connection.
 */
public class RabbitMQConnectionImpl implements RabbitMQConnection {

    private final String host;
    private Connection connection;

    /**
     * Create an object with the option to open a connection to the given host.
     *
     * @param host
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
            e.printStackTrace();
        } catch (final TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
