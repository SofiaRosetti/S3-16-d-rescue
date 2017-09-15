package it.unibo.mobileuser.connection;

/**
 * Interface implemented to perform different actions on publishing a message.
 */
public interface PublisherDelegate {

    /**
     * Actions to be performed after publishing the message.
     *
     * @param bool true if the message was correctly published, false otherwise
     */
    void onSendingRequest(boolean bool);

}
