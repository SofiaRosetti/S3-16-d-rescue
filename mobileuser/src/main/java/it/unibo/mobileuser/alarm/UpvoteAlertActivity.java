package it.unibo.mobileuser.alarm;

import android.os.Bundle;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.gps.GpsActivityImpl;

public class UpvoteAlertActivity extends GpsActivityImpl {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upvote_alert);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.alerts);

    }

}
