package it.unibo.mobileuser.authentication;

/**
 * Interface for mobileuser login.
 */
public interface LoginListener {

    /**
     * Performs login with the given parameters.
     */
    void login(String email, String password);
}
