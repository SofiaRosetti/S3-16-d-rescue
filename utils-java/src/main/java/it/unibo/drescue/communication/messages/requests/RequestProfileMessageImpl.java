package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;

/**
 * Class that represents a message containing a request of user data.
 */
public class RequestProfileMessageImpl extends AbstractMessage implements RequestProfileMessage {

    public static final String REQUEST_PROFILE_MESSAGE = "request_profile_message";

    private final int userID;

    /**
     * Creates a message containing the user's identifier for which data are requested.
     *
     * @param userID user's identifier
     */
    public RequestProfileMessageImpl(final int userID) {
        super(REQUEST_PROFILE_MESSAGE);
        this.userID = userID;
    }

    @Override
    public int getUserID() {
        return this.userID;
    }
}
