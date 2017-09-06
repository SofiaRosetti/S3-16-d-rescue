package it.unibo.drescue.communication;

import it.unibo.drescue.communication.messages.*;

import java.io.IOException;

public interface CpProducer {

    /**
     *
     * @param msg
     * @param routingKey
     * @throws IOException
     */
    void sendMessage(Message msg, String routingKey) throws IOException;
}
