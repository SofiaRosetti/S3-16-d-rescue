package it.unibo.mobileuser.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unibo.drescue.StringUtils;

import static it.unibo.mobileuser.utils.ServerUtils.*;

/**
 * Abstract and generic class that handles server response.
 */
public abstract class AbstractServerResponse<T> implements RequestDelegate {

    @Override
    public void onReceivingResponse(final String response) {
        if (StringUtils.isAValidString(response)) {
            final JsonObject json = new JsonParser().parse(response).getAsJsonObject();
            if (json != null) {
                final int code = json.get(CODE).getAsInt();
                if (code == RESPONSE_SUCCESS_CODE) {
                    onSuccessfulRequest((T) json.get(DATA));
                } else {
                    System.out.println("[AbstractServerResponse] code is NOT 200");
                    onErrorRequest(RESPONSE_ERROR_CODE);
                }
            } else {
                System.out.println("[AbstractServerResponse] json == null");
                onErrorRequest(RESPONSE_ERROR_CODE);
            }
        } else {
            System.out.println("[AbstractServerResponse] response is not valid");
            onErrorRequest(RESPONSE_ERROR_CODE);
        }
    }

    /**
     * Actions to perform when the request is successful.
     *
     * @param data JsonObject or JsonArray containing the response
     */
    public abstract void onSuccessfulRequest(T data);

    /**
     * Actions to perform when the request is not successful.
     *
     * @param code error code
     */
    public abstract void onErrorRequest(int code);

}