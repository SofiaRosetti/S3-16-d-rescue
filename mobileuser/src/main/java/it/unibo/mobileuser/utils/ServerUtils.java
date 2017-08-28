package it.unibo.mobileuser.utils;

import it.unibo.mobileuser.connection.RequestImpl;

/**
 * Class with specific methods for server requests.
 */
public class ServerUtils {

    public static final String SERVER_ADDRESS = "ec2-13-58-168-22.us-east-2.compute.amazonaws.com";
    public static final int PORT = 4321;

    //Pattern to handle server response
    public static final int RESPONSE_SUCCESS_CODE = 200;
    public static final int RESPONSE_ERROR_CODE = 400;
    public static final String CODE = "code";
    public static final String DATA = "data";

    //Json keys in server requests and response
    private static final String ALERT_ID = "alertID";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String SURNAME = "surname";
    private static final String USER_ID = "userID";

    /**
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param phone
     * @return a sign up request with the given parameters
     */
    public static RequestImpl signUp(final String email, final String password, final String name, final String surname, final String phone) {
        final RequestImpl signUpRequest = new RequestImpl();
        signUpRequest.putKeyValuePair(EMAIL, email);
        signUpRequest.putKeyValuePair(PASSWORD, password);
        signUpRequest.putKeyValuePair(NAME, name);
        signUpRequest.putKeyValuePair(SURNAME, surname);
        signUpRequest.putKeyValuePair(PHONE_NUMBER, phone);
        return signUpRequest;
    }

    /**
     * @param email
     * @param password
     * @return a login request with the given parameters
     */
    public static RequestImpl login(final String email, final String password) {
        final RequestImpl loginRequest = new RequestImpl();
        loginRequest.putKeyValuePair(EMAIL, email);
        loginRequest.putKeyValuePair(PASSWORD, password);
        return loginRequest;
    }

    /**
     * @param userID
     * @param alertID
     * @return a request from userID to upvote alertID
     */
    public static RequestImpl upvoteAlert(final String userID, final String alertID) {
        final RequestImpl upvoteRequest = new RequestImpl();
        upvoteRequest.putKeyValuePair(USER_ID, userID);
        upvoteRequest.putKeyValuePair(ALERT_ID, alertID);
        return upvoteRequest;
    }
}
