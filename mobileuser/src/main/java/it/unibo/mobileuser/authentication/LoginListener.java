package it.unibo.mobileuser.authentication;

/**
 * Interface for login.
 */
public interface LoginListener {

    /**
     * Performs login with the given parameters.
     */
    void login(String email, String password);
}
