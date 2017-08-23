package it.unibo.mobileuser.connection;

/**
 * Interface implemented to create a generic request to server.
 */
public interface RequestItem {

    /**
     * Insert a key/value pair inside a JsonObject
     *
     * @param key
     * @param value
     */
    void putKeyValuePair(String key, String value);
}
