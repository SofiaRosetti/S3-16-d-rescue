package it.unibo.mobileuser.authentication;

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

public class SignUpActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.sign_up);

        final MaterialEditText nameEditText = (MaterialEditText) findViewById(R.id.name_sign_up);
        final MaterialEditText surnameEditText = (MaterialEditText) findViewById(R.id.surname_sign_up);
        final MaterialEditText emailEditText = (MaterialEditText) findViewById(R.id.email_sign_up);
        final MaterialEditText phoneEditText = (MaterialEditText) findViewById(R.id.phone_sign_up);
        final MaterialEditText passwordEditText = (MaterialEditText) findViewById(R.id.password_sign_up);
        final MaterialEditText confirmPasswordEditText = (MaterialEditText) findViewById(R.id.confirm_password_sign_up);

        final Button signUpButton = (Button) findViewById(R.id.sign_up);
        signUpButton.setOnClickListener((View v) -> {

            final String name = Utils.getEditTextString(nameEditText);
            final String surname = Utils.getEditTextString(surnameEditText);
            final String email = Utils.getEditTextString(emailEditText);
            final String phone = Utils.getEditTextString(phoneEditText);
            final String password = Utils.getEditTextString(passwordEditText);
            final String confirmPassword = Utils.getEditTextString(confirmPasswordEditText);
            if (StringUtils.isAValidString(name) && StringUtils.isAValidString(surname) &&
                    StringUtils.isAValidString(email) && StringUtils.isAValidString(phone) &&
                    StringUtils.isAValidString(password) && StringUtils.isAValidString(confirmPassword)) {
                if (StringUtils.isAValidEmail(email)) {
                    if (password.equals(confirmPassword)) {
                        signUp(name, surname, email, phone, password);
                    } else {
                        showDialog(R.string.sign_up, R.string.password_mismatch);
                    }
                } else {
                    showDialog(R.string.sign_up, R.string.incorrect_email_format);
                }
            } else {
                showDialog(R.string.sign_up, R.string.fill_fields);
            }
        });
    }

    /**
     * Performs sign up with the given parameters.
     *
     * @param name
     * @param surname
     * @param email
     * @param phone
     * @param password
     */
    private void signUp(final String name, final String surname, final String email, final String phone, final String password) {

        System.out.println("[SignUpActivity] signUp: name=" + name + " surname=" + surname +
                " email=" + email + " phone=" + phone + " password=" + password);

        new RequestAsyncTask(ServerUtils.signUp(email, password, name, surname, phone),
                new AbstractServerResponse<JsonObject>() {
                    @Override
                    public void onSuccessfulRequest(final JsonObject data) {
                        Toast.makeText(SignUpActivity.this, R.string.sign_up_successful, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onErrorRequest(final int code) {
                        showDialog(R.string.sign_up, R.string.incorrect_sign_up_data);
                    }
                }).execute();
    }
}
