package it.unibo.drescue.model;

/**
 * An interface modelling a User.
 */
public interface User {

    /**
     * @return the user ID
     */
    int getUserID();

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
     * @return the user password
     */
    String getPassword();

    /**
     * @return the user phone number
     */
    String getPhoneNumber();
}
