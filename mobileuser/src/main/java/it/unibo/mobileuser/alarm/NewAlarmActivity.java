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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        setToolbar(true);

        //TODO: Change with an array of events coming from database
        final Spinner spinner = (Spinner) findViewById(R.id.event_type_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.event_type_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final TextView latitudeTextView = (TextView) findViewById(R.id.latitude_field);
        final TextView longitudeTextView = (TextView) findViewById(R.id.longitude_field);
        latitudeTextView.setText(getLatitude());
        longitudeTextView.setText(getLongitude());

        final Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO: Add method to send new alarm with event type, description, latitude and longitude to server.
            }
        });
    }


}
