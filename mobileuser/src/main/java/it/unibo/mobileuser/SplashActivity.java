package it.unibo.mobileuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import it.unibo.mobileuser.authentication.LoginActivity;
import it.unibo.mobileuser.authentication.SignUpActivity;
import it.unibo.mobileuser.utils.PreferencesKey;
import it.unibo.mobileuser.utils.Utils;

/**
 * Launcher activity of the App.
 */
public class SplashActivity extends ToolbarActivity {

    private static final int LOGIN_CODE = 100;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkIfUserIsLogged();

        setToolbar(false);

        final Button loginButton = (Button) findViewById(R.id.splash_login);
        loginButton.setOnClickListener((View v) -> {

            final Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN_CODE);
        });

        final TextView signUpButton = (TextView) findViewById(R.id.splash_sign_up);
        signUpButton.setOnClickListener((View v) -> {

            final Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        });

    }

    @Override
    protected void onResume() {
        setToolbarVisibility(false);
        checkIfUserIsLogged();
        super.onResume();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_CODE && resultCode == RESULT_OK) {
            goToMainActivity();
            finish();
        }
    }

    /**
     * Go to MainActivity because a user is logged.
     */
    private void goToMainActivity() {
        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Checks if a user is already logged when opening the app.
     */
    private void checkIfUserIsLogged() {
        final String userID = Utils.getUserDataFromSharedPreferences(getApplicationContext(), PreferencesKey.USER_ID);
        if (userID.length() != 0) {
            goToMainActivity();
            finish();
        }
    }
}
