package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a message containing login data.
 */
public class LoginMessageImpl extends AbstractMessage implements LoginMessage, MessageBuilder {

    private final String email;
    private final String password;

    /**
     * Creates a message containing user's login data.
     *
     * @param email    user's email
     * @param password user's password
     */
    public LoginMessageImpl(final String email, final String password) {
        super(MessageType.LOGIN_MESSAGE);
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Message build() {
        return this;
    }
}
