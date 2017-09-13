package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.requests.RequestUpvoteAlertMessageImpl;

public class RequestUpvoteAlertMessageBuilderImpl implements RequestUpvoteAlertMessageBuilder {

    private final RequestUpvoteAlertMessageImpl message;

    public RequestUpvoteAlertMessageBuilderImpl() {
        this.message = new RequestUpvoteAlertMessageImpl();
    }

    @Override
    public RequestUpvoteAlertMessageBuilder setUserID(final int userID) {
        this.message.setUserID(userID);
        return this;
    }

    @Override
    public RequestUpvoteAlertMessageBuilder setAlertID(final int alertID) {
        this.message.setAlertID(alertID);
        return this;
    }

    @Override
    public RequestUpvoteAlertMessageBuilder setDistrictID(final String districtID) {
        this.message.setDistrictID(districtID);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
