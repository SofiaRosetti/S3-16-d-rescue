package it.unibo.mobileuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * A class that allows to show user profile and manage interaction with the graphical interface.
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
