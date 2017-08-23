package it.unibo.mobileuser.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;

/**
 * A class that allows to show the graphical interface to change user's data.
 */
public class EditProfileActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setToolbar(true);

        final Button save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO: Add method to send changes of the user's data.
            }
        });
    }
}
