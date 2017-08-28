package it.unibo.mobileuser.alarm;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import it.unibo.drescue.model.Alert;
import it.unibo.drescue.model.AlertImplBuilder;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.gps.GpsActivityImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that allows the user to view and upvote last alerts.
 */
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
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.upvote)
                    .setMessage(R.string.upvote_message)
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        //TODO upvote alert
                        dialogInterface.dismiss();
                    })
                    .setNeutralButton(R.string.alert_negative_button, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .show();
        });

        //TODO requestAlerts

    }

}
