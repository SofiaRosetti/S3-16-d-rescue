package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.requests.SignUpMessageImpl;

public class SignUpMessageBuilderImpl implements SignUpMessageBuilder {

    private final SignUpMessageImpl message;

    public SignUpMessageBuilderImpl() {
        this.message = new SignUpMessageImpl();
    }


    @Override
    public SignUpMessageBuilder setName(final String name) {
        this.message.setName(name);
        return this;
    }

    @Override
    public SignUpMessageBuilder setSurname(final String surname) {
        this.message.setSurname(surname);
        return this;
    }

    @Override
    public SignUpMessageBuilder setEmail(final String email) {
        this.message.setEmail(email);
        return this;
    }

    @Override
    public SignUpMessageBuilder setPhoneNumber(final String phoneNumber) {
        this.message.setPhoneNumber(phoneNumber);
        return this;
    }

    @Override
    public SignUpMessageBuilder setPassword(final String password) {
        this.message.setPassword(password);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
