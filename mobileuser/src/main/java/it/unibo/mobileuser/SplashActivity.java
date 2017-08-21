package it.unibo.mobileuser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SplashActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

    }
}
