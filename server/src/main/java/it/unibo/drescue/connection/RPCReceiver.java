package it.unibo.drescue.connection;

/**
 * Interface modelling a receiver of requests.
 */
public interface RPCReceiver {

    /**
     * Methods called to perform the request.
     *
     * @param jsonMessage
     * @return the string containing the response in JsonFormat of the request
     */
    String accessDB(String jsonMessage);
}
