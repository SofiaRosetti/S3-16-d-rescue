package it.unibo.mobileuser.connection;

/**
 * Interface implemented for perform different actions after a client request.
 */
public interface RequestDelegate {

    /**
     * Actions to perform after receiving the response from server
     *
     * @param response received from server
     */
    void onReceivingResponse(String response);
}


