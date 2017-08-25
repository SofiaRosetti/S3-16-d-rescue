package it.unibo.mobileuser.connection;

import com.google.gson.JsonObject;
import it.unibo.mobileuser.utils.ServerUtils;

/**
 * Class that represents a generic request to server.
 */
public class RequestImpl implements Request {

    private final String address;
    private final int port;
    private final JsonObject requestData;

    /**
     * Create a new request with empty data.
     */
    public RequestImpl() {
        this.address = ServerUtils.SERVER_ADDRESS;
        this.port = ServerUtils.PORT;
        this.requestData = new JsonObject();
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public JsonObject getRequestData() {
        return this.requestData;
    }

    @Override
    public void putKeyValuePair(final String key, final String value) {
        this.requestData.addProperty(key, value);
    }
}
