package it.unibo.mobileuser;

import android.os.Bundle;
import com.google.gson.JsonObject;
import it.unibo.mobileuser.authentication.*;
import it.unibo.mobileuser.connection.AbstractServerResponse;
import it.unibo.mobileuser.connection.RequestAsyncTask;
import it.unibo.mobileuser.utils.ServerUtils;

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
        setFragment(new SignInFragment(), R.id.container, true);

        setToolbarVisibility(true);
        getSupportActionBar().setTitle(R.string.sign_in);
    }

    @Override
    public void onRequestLogin() {
        setFragment(new LoginFragment(), R.id.container, true);

        setToolbarVisibility(true);
        getSupportActionBar().setTitle(R.string.login);
    }

    @Override
    public void login(final String email, final String password) {
        System.out.println("[SplashActivity] login: email=" + email + " password=" + password);

        new RequestAsyncTask(ServerUtils.login(email, password),
                new AbstractServerResponse<JsonObject>() {

                    @Override
                    public void onSuccessfulRequest(final JsonObject data) {
                        System.out.println("[SplashActivity] login successful");
                        //TODO perform login saving userID in preferences
                    }

                    @Override
                    public void onErrorRequest(final int code) {
                        System.out.println("[SplashActivity] login error");
                        //TODO show error dialog
                    }
                }).execute();
    }

    @Override
    public void signIn(final String name, final String surname, final String email, final String phone, final String password) {
        System.out.println("[SplashActivity] signIn: name=" + name + " surname=" + surname +
                " email=" + email + " phone=" + phone + " password=" + password);

        new RequestAsyncTask(ServerUtils.signIn(email, password, name, surname, phone),
                new AbstractServerResponse<JsonObject>() {
                    @Override
                    public void onSuccessfulRequest(final JsonObject data) {
                        System.out.println("[SplashActivity] signIn successful");
                        //TODO get data and switch to SplashFragment
                    }

                    @Override
                    public void onErrorRequest(final int code) {
                        System.out.println("[SplashActivity] signIn error");
                        //TODO show error dialog
                    }
                }).execute();
    }
}
