package it.unibo.mobileuser.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
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

        final Button changePassword = (Button) findViewById(R.id.password_change_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(view.getContext(), ChangePasswordActivity.class));
            }
        });

        /* TODO: Uncomment when the functionality will be implemented
        final Button editProfile = (Button) findViewById(R.id.edit_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(view.getContext(), EditProfileActivity.class));
            }
        });*/

        final Button logoutProfile = (Button) findViewById(R.id.logout_button);
        logoutProfile.setOnClickListener((View view) -> {
            Utils.deleteSharedPreferences(getApplicationContext());
            finish();
        });
    }
}
