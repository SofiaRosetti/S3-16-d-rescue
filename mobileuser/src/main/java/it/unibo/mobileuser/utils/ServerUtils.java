package it.unibo.mobileuser.utils;

import it.unibo.mobileuser.connection.RequestImpl;

/**
 * Class with specific utils for server requests.
 */
public class ServerUtils {

    public static final String SERVER_ADDRESS = "ec2-13-58-168-22.us-east-2.compute.amazonaws.com";
    public static final int PORT = 4321;

    /**
     * To handle server response
     */
    public static final int RESPONSE_SUCCESS_CODE = 200;
    public static final int RESPONSE_ERROR_CODE = 400;
    public static final String CODE = "code";
    public static final String DATA = "data";

    /**
     * Json keys
     */
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PHONE_NUMBER = "phoneNumber";

    /**
     * Requests
     */
    public static RequestImpl signIn(final String email, final String password, final String name, final String surname, final String phone) {
        final RequestImpl signInRequest = new RequestImpl();
        signInRequest.putKeyValuePair(EMAIL, email);
        signInRequest.putKeyValuePair(PASSWORD, password);
        signInRequest.putKeyValuePair(NAME, name);
        signInRequest.putKeyValuePair(SURNAME, surname);
        signInRequest.putKeyValuePair(PHONE_NUMBER, phone);
        return signInRequest;
    }

    public static RequestImpl login(final String email, final String password) {
        final RequestImpl signInRequest = new RequestImpl();
        signInRequest.putKeyValuePair(EMAIL, email);
        signInRequest.putKeyValuePair(PASSWORD, password);
        return signInRequest;
    }
}
