package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.User;

/**
 * Class that represents a message containing a response of user data.
 */
public class ProfileMessageImpl extends AbstractMessage implements ProfileMessage, MessageBuilder {

    private final User user;

    /**
     * Creates a message containing the user data for which a request was made.
     *
     * @param user user data
     */
    public ProfileMessageImpl(final User user) {
        super(MessageType.PROFILE_MESSAGE);
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
