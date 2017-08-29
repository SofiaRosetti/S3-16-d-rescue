package it.unibo.mobileuser.alarm;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unibo.drescue.model.Alert;
import it.unibo.drescue.model.AlertImplBuilder;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.connection.AbstractServerResponse;
import it.unibo.mobileuser.connection.RequestAsyncTask;
import it.unibo.mobileuser.gps.GpsActivityImpl;
import it.unibo.mobileuser.utils.ServerUtils;
import it.unibo.mobileuser.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that allows the user to view and upvote last alerts.
 */
public class UpvoteAlertActivity extends GpsActivityImpl {

    private AlertAdapter alertAdapter;
    private List<Alert> alertList;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upvote_alert);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.alerts);

        this.alertList = new ArrayList<>();

        //TODO delete (example elements)
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
                        final Alert alert = this.alertList.get(position);
                        upvoteAlert(alert.getAlertID());
                        dialogInterface.dismiss();
                    })
                    .setNeutralButton(R.string.alert_negative_button, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .show();
        });

        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        this.swipeRefreshLayout.setOnRefreshListener(() -> {
            onRequestAlerts();
        });

        //TODO
        //onRequestAlerts();

    }

    /**
     * Performs the upvote by the logged user of the alert identified by alertID.
     *
     * @param alertID
     */
    private void upvoteAlert(final int alertID) {
        final String userID = Utils.getUserIDfromSharedPreferences(getApplicationContext());

        System.out.println("[UpvoteAlertActivity] upvoteAlert: userID=" + userID + " alertID=" + alertID);

        new RequestAsyncTask(ServerUtils.upvoteAlert(userID, String.valueOf(alertID)),
                new AbstractServerResponse<JsonObject>() {
                    @Override
                    public void onSuccessfulRequest(final JsonObject data) {
                        Toast.makeText(UpvoteAlertActivity.this, R.string.upvote_successful, Toast.LENGTH_LONG).show();
                        onRequestAlerts();
                    }

                    @Override
                    public void onErrorRequest(final int code) {
                        showDialog(R.string.upvote, R.string.incorrect_upvote);
                    }
                }).execute();
    }

    /**
     * Performs the request for the alerts of the district where the logged user is.
     */
    private void onRequestAlerts() {

        System.out.println("[UpvoteAlertActivity] onRequestAlerts");

        new RequestAsyncTask(ServerUtils.requestAlerts(getLatitude(), getLongitude()),
                new AbstractServerResponse<JsonArray>() {
                    @Override
                    public void onSuccessfulRequest(final JsonArray data) {
                        UpvoteAlertActivity.this.swipeRefreshLayout.setRefreshing(false);
                        //TODO
                        //alertAdapter.clear();
                        //get data and put into new list
                        //add list into adapter
                        //UpvoteAlertActivity.this.alertAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onErrorRequest(final int code) {
                        UpvoteAlertActivity.this.swipeRefreshLayout.setRefreshing(false);
                        showDialog(R.string.alerts, R.string.alerts_error);
                    }
                }).execute();
    }

}
