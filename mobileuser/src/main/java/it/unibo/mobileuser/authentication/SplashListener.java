package it.unibo.mobileuser.authentication;

/**
 * Interface for mobileuser splash screen
 */
public interface SplashListener {

    /**
     * Switch to sign in screen
     */
    void onRequestSignIn();

    /**
     * Switch to login screen
     */
    void onRequestLogin();
}
