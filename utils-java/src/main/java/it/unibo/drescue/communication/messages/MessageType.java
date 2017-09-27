package it.unibo.drescue.communication.messages;

/**
 * Enum of types of message which are allowed in communication.
 */
public enum MessageType {
    ALERTS_MESSAGE("alerts_message"),
    CHANGE_PASSWORD_MESSAGE("change_password_message"),
    CONFIGURATION_MESSAGE("configuration_message"),
    COORDINATION_MESSAGE("coordination_message"),
    REQ_COORDINATION_MESSAGE("req_coordination_message"),
    REPLAY_COORDINATION_MESSAGE("replay_coordination_message"),
    ERROR_MESSAGE("error_message"),
    LOGIN_MESSAGE("login_message"),
    NEW_ALERT_MESSAGE("new_alert_message"),
    UNKNOWN_MESSAGE(""),
    PROFILE_MESSAGE("profile_message"),
    REQUEST_MOBILE_ALERTS_MESSAGE("request_mobile_alerts_message"),
    REQUEST_CP_ALERTS_MESSAGE("request_cp_alerts_message"),
    REQUEST_PROFILE_MESSAGE("request_profile_message"),
    REQUEST_UPVOTE_MESSAGE("request_upvote_message"),
    RESPONSE_LOGIN_MESSAGE("response_login_message"),
    SIGN_UP_MESSAGE("sign_up_message"),
    SUCCESSFUL_MESSAGE("successful_message"),
    FORWARD_MESSAGE("forward_message"),
    FORWARD_ALERT_MESSAGE("forward_alert_message"),
    FORWARD_UPVOTED_ALERT_MESSAGE("forward_upvoted_alert_message"),
    CP_LOGIN_MESSAGE("cp_login_message"),
    RESCUE_TEAMS_MESSAGE("rescue_teams_message"),
    RESCUE_TEAMS_NOT_ENROLLED_MESSAGE("rescue_teams_not_enrolled_message"),
    ENROLL_RESCUE_TEAM_MESSAGE("enroll_rescue_team_message"),
    ADD_RESCUE_TEAM_MESSAGE("add_rescue_team_message"),
    REQ_RESCUE_TEAM_CONDITION("req_rescue_team_condition"),
    REPLY_RESCUE_TEAM_CONDITION("reply_rescue_team_condition"),
    ASSOCIATED_CPS_MESSAGE("associated_cps_message"),
    CPS_MESSAGE("cps_message"),
    FAKE_RPC_MESSAGE("fake_rpc_message"),
    FAKE_FORWARD_MESSAGE("fake_forward_message");


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
