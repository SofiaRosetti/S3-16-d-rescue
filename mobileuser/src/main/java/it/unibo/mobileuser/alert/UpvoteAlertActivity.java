package it.unibo.mobileuser.alert;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.Toast;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.builder.requests.RequestUpvoteAlertMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.MessageUtils;
import it.unibo.drescue.communication.messages.requests.RequestAlertsMessageImpl;
import it.unibo.drescue.communication.messages.response.AlertsMessageImpl;
import it.unibo.drescue.connection.QueueType;
import it.unibo.drescue.model.Alert;
import it.unibo.drescue.model.AlertImpl;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.connection.AbstractResponse;
import it.unibo.mobileuser.connection.RabbitAsyncTask;
import it.unibo.mobileuser.connection.RabbitPublishAsyncTask;
import it.unibo.mobileuser.gps.GpsActivityImpl;
import it.unibo.mobileuser.utils.PreferencesKey;
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
        getSupportActionBar().setTitle(R.string.last_alerts);

        final String userID = Utils.getUserDataFromSharedPreferences(getApplicationContext(), PreferencesKey.USER_ID);

        this.alertList = new ArrayList<>();
        this.alertAdapter = new AlertAdapter(this, this.alertList);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(this.alertAdapter);
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            new AlertDialog.Builder(this, R.style.alert_dialog_theme)
                    .setTitle(R.string.upvote)
                    .setMessage(R.string.upvote_message)
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        final Alert alert = this.alertList.get(position);

                        Message message = new RequestUpvoteAlertMessageBuilderImpl()
                                .setAlertID(alert.getAlertID())
                                .setDistrictID(alert.getDistrictID())
                                .setUserID(Integer.valueOf(userID))
                                .build();

                        upvoteAlert(message);

                        dialogInterface.dismiss();
                    })
                    .setNeutralButton(R.string.alert_negative_button, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .show();
        });

        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        this.swipeRefreshLayout.setOnRefreshListener(this::onRequestAlerts);

        onRequestAlerts();

    }

    /**
     * Performs the upvote by the logged user of the alert identified by alertID.
     *
     * @param message message containing the upvote
     */
    private void upvoteAlert(final Message message) {

        new RabbitPublishAsyncTask(QueueType.ALERTS_QUEUE.getQueueName(),
                message,
                bool -> {
                    if (bool)
                        showDialog(R.string.upvote_sent, R.string.upvote_sent_successful);
                    else
                        Toast.makeText(UpvoteAlertActivity.this, R.string.alert_sent_error, Toast.LENGTH_LONG).show();

                }).execute();
    }

    /**
     * Performs the request for the alerts of the district where the logged user is.
     */
    private void onRequestAlerts() {

        final String latitudeString = getLatitude();
        final double latitude = convertCoordinate(latitudeString);
        if (latitude != ERROR_VALUE) {
            final String longitudeString = getLongitude();
            final double longitude = convertCoordinate(longitudeString);
            if (longitude != ERROR_VALUE) {

                final Message message = new RequestAlertsMessageImpl(latitude, longitude).build();

                showProgressDialog();

                new RabbitAsyncTask(QueueType.ALERTS_QUEUE.getQueueName(),
                        message,
                        new AbstractResponse() {

                            @Override
                            public void onSuccessfulRequest(final String response) {
                                UpvoteAlertActivity.this.swipeRefreshLayout.setRefreshing(false);
                                dismissProgressDialog();
                                if (MessageUtils.getMessageNameByJson(response) == MessageType.ALERTS_MESSAGE) {
                                    final AlertsMessageImpl alertsMessage = GsonUtils.fromGson(response, AlertsMessageImpl.class);
                                    final List<AlertImpl> list = alertsMessage.getAlerts();
                                    if (list.size() == 0)
                                        Toast.makeText(UpvoteAlertActivity.this, R.string.alerts_missing, Toast.LENGTH_LONG).show();
                                    UpvoteAlertActivity.this.alertAdapter.clear();
                                    UpvoteAlertActivity.this.alertAdapter.addAll(list);
                                    UpvoteAlertActivity.this.alertAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onErrorRequest(final String errorMessage) {
                                UpvoteAlertActivity.this.swipeRefreshLayout.setRefreshing(false);
                                dismissProgressDialog();
                                showDialog(R.string.last_alerts, R.string.alerts_error);
                            }

                        }).execute();
            }
        }

    }
}
