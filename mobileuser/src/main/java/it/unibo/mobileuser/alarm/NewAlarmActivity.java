package it.unibo.mobileuser.alarm;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import it.unibo.mobileuser.R;

/**
 * A class that allows to show graphical interface to report new alarm and sends the alarm to server.
 */
public class NewAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        //TODO: Change with an array of events coming from database
        final Spinner spinner = (Spinner) findViewById(R.id.event_type_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.event_type_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO: Add method to send new alarm with event type, description, latitude and longitude to server.
            }
        });
    }


}
