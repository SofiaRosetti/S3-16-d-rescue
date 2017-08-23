package it.unibo.mobileuser.connection;

import com.google.gson.JsonObject;
import it.unibo.mobileuser.utils.ServerUtils;

/**
 * Class that represents a generic request to server.
 */
public class RequestItemImpl implements RequestItem {

    private final String address;
    private final int port;
    private final JsonObject requestData;

    /**
     * Create a new request with empty data.
     */
    public RequestItemImpl() {
        this.address = ServerUtils.SERVER_ADDRESS;
        this.port = ServerUtils.PORT;
        this.requestData = new JsonObject();
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    public JsonObject getRequestData() {
        return this.requestData;
    }

    @Override
    public void putKeyValuePair(final String key, final String value) {
        this.requestData.addProperty(key, value);
    }
}
