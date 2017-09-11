package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a message for performing the request to change password.
 */
public interface ChangePasswordMessage {

    /**
     * @return the user identifier
     */
    int getUserID();

    /**
     * Sets the user identifier.
     *
     * @param userID user identifier
     */
    void setUserID(int userID);

    /**
     * @return the old password
     */
    String getOldPassword();

    /**
     * Sets the old password of user.
     *
     * @param oldPassword user's old password
     */
    void setOldPassword(String oldPassword);

    /**
     * @return the new password
     */
    String getNewPassword();

    /**
     * Sets the new password of user.
     *
     * @param newPassword user's new password
     */
    void setNewPassword(String newPassword);

}
