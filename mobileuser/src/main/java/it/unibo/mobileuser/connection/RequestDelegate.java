package it.unibo.mobileuser.connection;

/**
 * Interface implemented to perform different actions on RPC client request.
 */
public interface RequestDelegate {

    /**
     * Actions to be performed after receiving server response.
     *
     * @param response received from server
     */
    void onReceivingResponse(String response);
}


