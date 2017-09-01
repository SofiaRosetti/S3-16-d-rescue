package it.unibo.drescue.connection.client;

/**
 * Interface modelling a sender of requests.
 */
public interface RPCSender {

    /**
     * Send a request with the given jsonMessage.
     *
     * @param jsonMessage message containing the request
     * @return the request response
     */
    String doRequest(String jsonMessage);
}
