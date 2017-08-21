package it.unibo.mobileuser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import it.unibo.mobileuser.authentication.SplashListener;

public class SplashActivity extends BaseActivity
        implements SplashListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

    }

    @Override
    public void onRequestSignIn() {
        //TODO switch to SignInFragment
    }

    @Override
    public void onRequestLogin() {
        //TODO switch to LoginFragment
    }
}
