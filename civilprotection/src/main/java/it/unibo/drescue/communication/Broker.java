package it.unibo.drescue.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Broker {

    /**
     *
     */
    void createConnection(String[] bindingQueue) throws IOException, TimeoutException;

    /**
     *
     */
    void newConsumer() throws IOException;

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
