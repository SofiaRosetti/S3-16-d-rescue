package it.unibo.mobileuser.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import it.unibo.drescue.StringUtils;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
import it.unibo.mobileuser.connection.AbstractServerResponse;
import it.unibo.mobileuser.connection.RequestAsyncTask;
import it.unibo.mobileuser.utils.ServerUtils;
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
                login(email, password);
            } else {
                showDialog(R.string.login, R.string.incorrect_login_data);
            }
        });
    }

    /**
     * Performs login with the given parameters.
     *
     * @param email
     * @param password
     */
    private void login(final String email, final String password) {

        System.out.println("[LoginActivity] login: email=" + email + " password=" + password);

        new RequestAsyncTask(ServerUtils.login(email, password),
                new AbstractServerResponse<JsonObject>() {

                    @Override
                    public void onSuccessfulRequest(final JsonObject data) {
                        Toast.makeText(LoginActivity.this, R.string.login_successful, Toast.LENGTH_LONG).show();

                        final String userID = data.get("id").getAsString();
                        Utils.setUserIDinSharedPreferences(getApplicationContext(), userID);

                        final Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onErrorRequest(final int code) {
                        showDialog(R.string.login, R.string.incorrect_login_data);
                    }
                }).execute();
    }
}
