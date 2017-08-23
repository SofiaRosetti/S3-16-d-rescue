package it.unibo.mobileuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import it.unibo.mobileuser.alarm.NewAlarmActivity;
import it.unibo.mobileuser.profile.ProfileActivity;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(false);

        final Button newAlertButton = (Button) findViewById(R.id.button_new_alarm);
        newAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(view.getContext(), NewAlarmActivity.class));
            }
        });

        final Button profileButton = (Button) findViewById(R.id.button_profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(view.getContext(), ProfileActivity.class));
            }
        });
    }
}
