package it.unibo.drescue.model;

/**
 * An interface modelling a User.
 */
public interface User extends LoggableModel {

    /**
     * @return the user ID
     */
    int getUserID();

    /**
     * Sets the user ID
     *
     * @param userID the user ID
     */
    void setUserID(int userID);

    /**
     * @return the user name
     */
    String getName();

    /**
     * Sets the user name
     *
     * @param name the user name
     */
    void setName(String name);

    /**
     * @return the user surname
     */
    String getSurname();

    /**
     * Sets the user surname
     *
     * @param surname the user surname
     */
    void setSurname(String surname);

    /**
     * @return the user email
     */
    String getEmail();

    /**
     * Sets the user email
     *
     * @param email the user email
     */
    void setEmail(String email);

    /**
     * @return the user phone number
     */
    String getPhoneNumber();

    /**
     * Sets the user phone number
     *
     * @param phoneNumber the user phone number
     */
    void setPhoneNumber(String phoneNumber);
}
