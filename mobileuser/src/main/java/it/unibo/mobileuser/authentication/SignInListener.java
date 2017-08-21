package it.unibo.mobileuser.authentication;

/**
 * Interface for mobileuser sign in.
 */
public interface SignInListener {

    /**
     * Performs sign in with the given parameters.
     */
    void signIn(String name, String surname, String email, String phone,
                String password, String confirmPassword);

}
