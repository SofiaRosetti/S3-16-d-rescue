package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Builder interface for sign up messages.
 */
public interface SignUpMessageBuilder extends MessageBuilder {

    /**
     * @param name
     * @return the builder with the given name
     */
    SignUpMessageBuilder setName(String name);

    /**
     * @param surname
     * @return the builder with the given surname
     */
    SignUpMessageBuilder setSurname(String surname);

    /**
     * @param email
     * @return the builder with the given email
     */
    SignUpMessageBuilder setEmail(String email);

    /**
     * @param phoneNumber
     * @return the builder with the given phoneNumber
     */
    SignUpMessageBuilder setPhoneNumber(String phoneNumber);

    /**
     * @param password
     * @return the builder with the given password
     */
    SignUpMessageBuilder setPassword(String password);

}
