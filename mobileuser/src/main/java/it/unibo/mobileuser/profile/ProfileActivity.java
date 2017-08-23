package it.unibo.mobileuser.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;

/**
 * A class that allows to show user profile and manage interaction with the graphical interface.
 */
public class ProfileActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setToolbar(true);

        final Button changePassword = (Button) findViewById(R.id.password_change_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(view.getContext(), ChangePasswordActivity.class));
            }
        });

        final Button editProfile = (Button) findViewById(R.id.edit_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(view.getContext(), EditProfileActivity.class));
            }
        });
    }
}
