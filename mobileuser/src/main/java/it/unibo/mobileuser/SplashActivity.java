package it.unibo.mobileuser;

import android.os.Bundle;
import it.unibo.mobileuser.authentication.*;

/**
 * Launcher activity of the App.
 */
public class SplashActivity extends ToolbarActivity
        implements SplashListener, SignInListener, LoginListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setToolbar(false);

        setFragment(new SplashFragment(), R.id.container, false);

    }

    @Override
    public void onRequestSignIn() {
        System.out.println("[SplashActivity] onRequestSignIn");
        setFragment(new SignInFragment(), R.id.container, true);

        setToolbarVisibility(true);
        getSupportActionBar().setTitle(R.string.sign_in);
    }

    @Override
    public void onRequestLogin() {
        System.out.println("[SplashActivity] onRequestLogin");
        setFragment(new LoginFragment(), R.id.container, true);

        setToolbarVisibility(true);
        getSupportActionBar().setTitle(R.string.login);
    }

    @Override
    public void login(final String email, final String password) {
        //TODO asynkTask request
    }

    @Override
    public void signIn(final String name, final String surname, final String email, final String phone, final String password) {
        //TODO asynkTask request
    }
}
