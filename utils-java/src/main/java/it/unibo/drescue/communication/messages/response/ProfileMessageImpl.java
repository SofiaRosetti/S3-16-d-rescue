package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.model.User;

/**
 * Class that represents a message containing a response of user data.
 */
public class ProfileMessageImpl extends AbstractMessage implements ProfileMessage, MessageBuilder {

    public static final String PROFILE_MESSAGE = "profile_message";

    private final User user;

    /**
     * Creates a message containing the user data for which a request was made.
     *
     * @param user user data
     */
    public ProfileMessageImpl(final User user) {
        super(PROFILE_MESSAGE);
        this.user = user;
    }

    @Override
    public User getProfile() {
        return this.user;
    }

    @Override
    public Message build() {
        return this;
    }
}
