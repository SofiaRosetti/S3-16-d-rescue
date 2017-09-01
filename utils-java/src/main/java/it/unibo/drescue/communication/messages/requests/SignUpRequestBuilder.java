package it.unibo.drescue.communication.messages.requests;

/**
 * Builder interface for sign up requests.
 */
public interface SignUpRequestBuilder {

    /**
     * @param name
     * @return the builder with the given name
     */
    SignUpRequestBuilder setName(String name);

    /**
     * @param surname
     * @return the builder with the given surname
     */
    SignUpRequestBuilder setSurname(String surname);

    /**
     * @param email
     * @return the builder with the given email
     */
    SignUpRequestBuilder setEmail(String email);

    /**
     * @param phoneNumber
     * @return the builder with the given phoneNumber
     */
    SignUpRequestBuilder setPhoneNumber(String phoneNumber);

    /**
     * @param password
     * @return the builder with the given password
     */
    SignUpRequestBuilder setPassword(String password);

    /**
     * @return a sign up request
     */
    SignUpRequestImpl build();
}
