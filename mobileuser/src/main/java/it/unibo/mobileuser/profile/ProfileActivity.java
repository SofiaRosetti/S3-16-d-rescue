package it.unibo.mobileuser.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
import it.unibo.mobileuser.utils.PreferencesKey;
import it.unibo.mobileuser.utils.Utils;

/**
 * A class that allows to show user profile and manage interaction with the graphical interface.
 */
public class ProfileActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.profile);

        setEditText(R.id.name_field, PreferencesKey.USER_NAME);
        setEditText(R.id.surname_field, PreferencesKey.USER_SURNAME);
        setEditText(R.id.email_field, PreferencesKey.USER_EMAIL);
        setEditText(R.id.phone_field, PreferencesKey.USER_PHONE);

        final Button changePassword = (Button) findViewById(R.id.password_change_button);
        changePassword.setOnClickListener((View view) -> {
            startActivity(new Intent(view.getContext(), ChangePasswordActivity.class));
        });

        final Button logoutProfile = (Button) findViewById(R.id.logout_button);
        logoutProfile.setOnClickListener((View view) -> {
            Utils.deleteSharedPreferences(getApplicationContext());
            finish();
        });
    }

    /**
     * Set text in a specific Edit Text.
     *
     * @param id  id of the edit text to set
     * @param key name of preference to get
     */
    private void setEditText(final int id, final PreferencesKey key) {
        final TextView editText = (TextView) findViewById(id);
        editText.setText(Utils.getUserDataFromSharedPreferences(getApplicationContext(), key));
    }
}
