package it.unibo.mobileuser.alarm;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.gps.GpsActivityImpl;

/**
 * A class that allows to show graphical interface to report new alarm and sends the alarm to server.
 */
public class NewAlarmActivity extends GpsActivityImpl {

    private static final double ERROR_VALUE = -2000;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.new_alarm);

        //TODO: Change with an array of events coming from database
        final Spinner spinner = (Spinner) findViewById(R.id.event_type_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.event_type_array,
                R.layout.spinner_event_type_item);
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
            final double latitude = convertCoordinate(latitudeString);
            if (latitude != ERROR_VALUE) {
                final String longitudeString = longitudeTextView.getText().toString();
                final double longitude = convertCoordinate(longitudeString);
                if (longitude != ERROR_VALUE) {
                    /* TODO: insert userID and event type
                    Message message = new NewAlertMessageBuilderImpl()
                            .setUserID()
                            .setEventType()
                            .setLatitude(latitude)
                            .setLongitude(longitude)
                            .build();
                     */
                }
            }
        });
    }

    /**
     * Converts the coordinate from string to double. If the coordinate is not
     * a double value, it shows a message.
     *
     * @param value the coordinate to convert
     * @return the converted coordinate
     */
    private double convertCoordinate(final String value) {
        double convertedValue = ERROR_VALUE;
        try {
            convertedValue = Double.parseDouble(value);
        } catch (final NumberFormatException ex) {
            showDialog(R.string.attention, R.string.gps_unavailable);
        }
        return convertedValue;
    }


}
