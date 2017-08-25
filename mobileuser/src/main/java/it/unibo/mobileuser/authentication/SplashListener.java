package it.unibo.mobileuser.authentication;

/**
 * Interface for splash screen.
 */
public interface SplashListener {

    /**
     * Switch to sign in fragment.
     */
    void onRequestSignIn();

    /**
     * Switch to login fragment.
     */
    void onRequestLogin();
}
