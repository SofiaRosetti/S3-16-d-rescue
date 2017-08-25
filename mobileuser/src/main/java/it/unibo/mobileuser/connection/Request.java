package it.unibo.mobileuser.connection;

import com.google.gson.JsonObject;

/**
 * Interface for a generic request to server.
 */
public interface Request {

    /**
     * @return the server address
     */
    String getAddress();

    /**
     * @return the server port
     */
    int getPort();

    /**
     * @return the data to send
     */
    JsonObject getRequestData();

    /**
     * Insert a key/value pair inside a request.
     *
     * @param key
     * @param value
     */
    void putKeyValuePair(String key, String value);
}
