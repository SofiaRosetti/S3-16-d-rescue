package it.unibo.drescue.communication.messages;

/**
 * Enum of types of message which are allowed in communication.
 */
public enum MessageType {
    ALERTS_MESSAGE("alerts_message"),
    CHANGE_PASSWORD_MESSAGE("change_password_message"),
    ERROR_MESSAGE("error_message"),
    LOGIN_MESSAGE("login_message"),
    NEW_ALERT_MESSAGE("new_alert_message"),
    UNKNOWN_MESSAGE(""),
    PROFILE_MESSAGE("profile_message"),
    REQUEST_ALERTS_MESSAGE("request_alerts_message"),
    REQUEST_PROFILE_MESSAGE("request_profile_message"),
    SIGN_UP_MESSAGE("sign_up_message"),
    SUCCESSFUL_MESSAGE("successful_message");

    private final String messageType;

    /**
     * Creates a message type well formed.
     *
     * @param messageType the type of message
     */
    MessageType(final String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the type of message
     */
    public String getMessageType() {
        return this.messageType;
    }
}
