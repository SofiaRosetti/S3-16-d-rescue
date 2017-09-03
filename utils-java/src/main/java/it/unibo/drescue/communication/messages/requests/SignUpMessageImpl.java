package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;

/**
 * Class that represents a message containing sign up data.
 */
public class SignUpMessageImpl extends AbstractMessage implements SignUpMessage {

    public final static String SIGN_UP_MESSAGE = "sign_up_message";

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;

    public SignUpMessageImpl() {
        super(SIGN_UP_MESSAGE);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
}
