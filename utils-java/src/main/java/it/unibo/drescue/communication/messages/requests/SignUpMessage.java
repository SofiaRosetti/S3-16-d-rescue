package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a message for performing sign up.
 */
public interface SignUpMessage {

    /**
     * @return the user name
     */
    String getName();

    /**
     * Sets the user's name.
     *
     * @param name
     */
    void setName(String name);

    /**
     * @return the user surname
     */
    String getSurname();

    /**
     * Sets the user's surname.
     *
     * @param surname
     */
    void setSurname(String surname);

    /**
     * @return the user email
     */
    String getEmail();

    /**
     * Sets the user's email.
     *
     * @param email
     */
    void setEmail(String email);

    /**
     * @return the user's phone number
     */
    String getPhoneNumber();

    /**
     * Sets the user's phone number.
     *
     * @param phoneNumber
     */
    void setPhoneNumber(String phoneNumber);

    /**
     * @return the user password
     */
    String getPassword();

    /**
     * Sets the user's password.
     *
     * @param password
     */
    void setPassword(String password);
}
