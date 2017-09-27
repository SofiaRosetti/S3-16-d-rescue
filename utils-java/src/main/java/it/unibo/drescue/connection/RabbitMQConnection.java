package it.unibo.drescue.connection;

import com.rabbitmq.client.Connection;

/**
 * Interface modelling a connection.
 */
public interface RabbitMQConnection {

    /**
     * @return the connection to the broker
     */
    Connection getConnection();

    /**
     * Open connection to the broker.
     */
    void openConnection();

    /**
     * Close connection to he broker.
     */
    void closeConnection();
}
