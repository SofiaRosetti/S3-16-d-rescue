package it.unibo.drescue.communication;

import it.unibo.drescue.message.JSONMessage;
import java.io.IOException;

public interface CpProducer {

    /**
     *
     * @param msg
     * @param routingKey
     * @throws IOException
     */
    void sendMessage(JSONMessage msg, String routingKey) throws IOException;
}
