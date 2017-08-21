package it.unibo.mobileuser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import it.unibo.mobileuser.authentication.SplashFragment;
import it.unibo.mobileuser.authentication.SplashListener;

/**
 * Launcher activity of the App.
 */
public class SplashActivity extends BaseActivity
        implements SplashListener {

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
    public void onRequestSignIn() {
        //TODO switch to SignInFragment
    }

    @Override
    public void onRequestLogin() {
        //TODO switch to LoginFragment
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
