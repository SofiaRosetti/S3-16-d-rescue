package it.unibo.drescue.connection.messages.requests;

/**
 * An interface modelling a sign up request.
 */
public interface SignUpRequest {

    /**
     * @return the user name
     */
    String getName();

    /**
     * @return the user surname
     */
    String getSurname();

    /**
     * @return the user email
     */
    String getEmail();

    /**
     * @return the user phone number
     */
    String getPhoneNumber();

    /**
     * @return the user password
     */
    String getPassword();

}
