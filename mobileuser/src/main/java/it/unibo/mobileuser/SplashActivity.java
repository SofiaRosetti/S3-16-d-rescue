package it.unibo.mobileuser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import it.unibo.mobileuser.authentication.*;

/**
 * Launcher activity of the App.
 */
public class SplashActivity extends BaseActivity
        implements SplashListener, SignInListener, LoginListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        setToolbarVisibility(false);

        setFragment(new SplashFragment(), R.id.container, false);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setToolbarVisibility(false);
        super.onBackPressed();
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

    /**
     * Set the visibility of the toolbar inside an activity.
     *
     * @param visible if true the toolbar is visible inside the activity
     */
    private void setToolbarVisibility(final boolean visible) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
            getSupportActionBar().setHomeButtonEnabled(visible);
            getSupportActionBar().setDisplayShowTitleEnabled(visible);
        }
    }
}
