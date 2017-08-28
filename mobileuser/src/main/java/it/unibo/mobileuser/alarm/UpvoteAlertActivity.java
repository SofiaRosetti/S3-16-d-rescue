package it.unibo.mobileuser.alarm;

import android.os.Bundle;
import android.widget.ListView;
import it.unibo.drescue.model.Alert;
import it.unibo.drescue.model.AlertImplBuilder;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.gps.GpsActivityImpl;

import java.util.ArrayList;
import java.util.List;

public class UpvoteAlertActivity extends GpsActivityImpl {

    private AlertAdapter alertAdapter;
    private List<Alert> alertList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upvote_alert);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.alerts);

        this.alertList = new ArrayList<>();

        //TODO example elements
        for (int i = 0; i < 10; i++) {
            this.alertList.add(new AlertImplBuilder().createAlertImpl());
        }

        this.alertAdapter = new AlertAdapter(this, this.alertList);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(this.alertAdapter);

    }

}
