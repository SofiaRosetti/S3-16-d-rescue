package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a message for performing the request to change password.
 */
public interface ChangePasswordMessage {

    /**
     * @return the user email
     */
    String getUserEmail();

    /**
     * Sets the user email.
     *
     * @param userEmail user email
     */
    void setUserEmail(String userEmail);

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
