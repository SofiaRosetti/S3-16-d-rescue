package it.unibo.mobileuser.connection;

import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;

/**
 * Abstract class to handle server response.
 */
public abstract class AbstractResponse implements RequestDelegate {

    @Override
    public void onReceivingResponse(final String response) {
        if (StringUtils.isAValidString(response)) {
            final String messageType = StringUtils.getMessageType(response);
            switch (messageType) {
                case ErrorMessageImpl.ERROR_MESSAGE:
                    final ErrorMessageImpl errorMessage = GsonUtils.fromGson(response, ErrorMessageImpl.class);
                    onErrorRequest(errorMessage.getError());
                    break;
                default:
                    onSuccessfulRequest(response);
                    break;
            }
        } else {
            onErrorRequest("Error on contacting server.");
        }
    }

    /**
     * Actions to perform when the request is successful.
     *
     * @param response message containing the response
     */
    public abstract void onSuccessfulRequest(String response);

    /**
     * Actions to perform when the request is not successful.
     *
     * @param errorMessage string containing the error
     */
    public abstract void onErrorRequest(String errorMessage);

}
