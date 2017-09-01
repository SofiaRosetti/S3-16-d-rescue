package it.unibo.drescue.connection;

/**
 * Class that handles the creation of every channel on which the
 * server can receive requests.
 */
public class ChannelSetup {

    /**
     * Creates channel on the given connection.
     *
     * @param connection
     */
    public ChannelSetup(final RabbitMQConnection connection) {

        //TODO setup channels

        new AuthenticationRPC(connection.getConnection(), ServerUtils.AUTHENTICATION_CHANNEL_RPC);

    }
}
