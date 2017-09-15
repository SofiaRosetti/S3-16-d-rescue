package it.unibo.mobileuser.alarm;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import it.unibo.drescue.communication.builder.requests.NewAlertMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.connection.QueueType;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.connection.RabbitPublishAsyncTask;
import it.unibo.mobileuser.gps.GpsActivityImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that allows to show graphical interface to report new alarm and sends the alarm to server.
 */
public class NewAlarmActivity extends GpsActivityImpl {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.new_alarm);

        //TODO example of event types
        final Set<String> set = new HashSet<>();
        set.add("Earthquakes");
        set.add("Floods");
        //TODO change with commented line
        final List<String> arrayList = new ArrayList<>();
        arrayList.addAll(set);
        //arrayList.addAll(Utils.getDataSetFromSharedPreferences(getApplicationContext()));

        final Spinner spinner = (Spinner) findViewById(R.id.event_type_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                NewAlarmActivity.this, R.layout.spinner_event_type_item, arrayList);
        adapter.setDropDownViewResource(R.layout.spinner_event_type_dropdown_item);
        spinner.setAdapter(adapter);

        final TextView latitudeTextView = (TextView) findViewById(R.id.latitude_field);
        final TextView longitudeTextView = (TextView) findViewById(R.id.longitude_field);
        latitudeTextView.setText(getLatitude());
        longitudeTextView.setText(getLongitude());

        final Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener((View view) -> {
            final String eventType = spinner.getSelectedItem().toString();
            final String latitudeString = latitudeTextView.getText().toString();
            //TODO final double latitude = convertCoordinate(latitudeString);
            final double latitude = 44.420826;
            if (latitude != ERROR_VALUE) {
                final String longitudeString = longitudeTextView.getText().toString();
                //TODO final double longitude = convertCoordinate(longitudeString);
                final double longitude = 11.912387;
                if (longitude != ERROR_VALUE) {

                    //TODO change - example
                    final Message message = new NewAlertMessageBuilderImpl()
                            .setUserID(4) //TODO get userID from shared preferences
                            .setEventType(eventType)
                            .setLatitude(latitude)
                            .setLongitude(longitude)
                            .build();

                    sendNewAlert(message);
                }
            }
        });
    }

    /**
     * Performs the sending of a new alert.
     *
     * @param message message containing the alert
     */
    private void sendNewAlert(final Message message) {

        new RabbitPublishAsyncTask(QueueType.ALERTS_QUEUE.getQueueName(),
                message,
                bool -> {
                    if (bool) {
                        Toast.makeText(NewAlarmActivity.this, R.string.alert_sent, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(NewAlarmActivity.this, R.string.alert_sent_error, Toast.LENGTH_LONG).show();
                    }

                }).execute();
    }


}
