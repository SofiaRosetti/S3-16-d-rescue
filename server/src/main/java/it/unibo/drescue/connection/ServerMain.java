package it.unibo.drescue.connection;

public class ServerMain {

    public static void main(final String[] args) {

        final RabbitMQConnection rabbitMQConnection = new RabbitMQConnectionImpl("localhost");
        rabbitMQConnection.openConnection();

        new ChannelSetup(rabbitMQConnection);

    }

}
