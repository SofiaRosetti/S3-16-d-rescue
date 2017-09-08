package it.unibo.drescue.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Broker {

    /**
     * Create a new connection to Broker
     */
    void createConnection(String[] bindingQueue) throws IOException, TimeoutException;

    /**
     * Add a new consumer
     */
    void newConsumer(Consumer consumer) throws IOException;

    /**
     *
     * @return
     */
    Channel getChannel();

    /**
     *
     * @return
     */
    String getQueueName();
}
