package it.unibo.mobileuser.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.rengwuxian.materialedittext.MaterialEditText;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
import it.unibo.mobileuser.utils.Utils;

public class LoginActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.login);

        final MaterialEditText emailEditText = (MaterialEditText) findViewById(R.id.email_login);
        final MaterialEditText passwordEditText = (MaterialEditText) findViewById(R.id.password_login);

        final Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener((View v) -> {

            final String email = Utils.getEditTextString(emailEditText);
            final String password = Utils.getEditTextString(passwordEditText);
            if (StringUtils.isAValidString(email) && StringUtils.isAValidString(password)
                    && StringUtils.isAValidEmail(email)) {
                //TODO login();
            } else {
                showDialog(R.string.login, R.string.incorrect_login_data);
            }
        });
    }

    /**
     * Performs login with the given parameters.
     *
     * @param message message containing user's login data
     */
    private void login(final Message message) {
        //TODO
    }
}
