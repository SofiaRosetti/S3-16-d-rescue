package it.unibo.mobileuser.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.builder.requests.SignUpMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.MessageUtils;
import it.unibo.drescue.connection.QueueType;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
import it.unibo.mobileuser.connection.AbstractResponse;
import it.unibo.mobileuser.connection.RabbitAsyncTask;
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
                    if (StringUtils.isAValidPassword(password)) {
                        if (password.equals(confirmPassword)) {

                            final Message message = new SignUpMessageBuilderImpl()
                                    .setName(name)
                                    .setSurname(surname)
                                    .setEmail(email)
                                    .setPhoneNumber(phone)
                                    .setPassword(password)
                                    .build();

                            signUp(message);
                        } else {
                            showDialog(R.string.sign_up, R.string.password_mismatch);
                        }
                    } else {
                        showDialog(R.string.sign_up, R.string.incorrect_password_format);
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
     * Performs sign up request with the given message.
     *
     * @param message message containing user's sign up data
     */
    private void signUp(final Message message) {

        showProgressDialog();

        new RabbitAsyncTask(QueueType.MOBILEUSER_QUEUE.getQueueName(),
                message,
                new AbstractResponse() {

                    @Override
                    public void onSuccessfulRequest(final String response) {
                        dismissProgressDialog();
                        if (MessageUtils.getMessageNameByJson(response) == MessageType.SUCCESSFUL_MESSAGE) {
                            Toast.makeText(SignUpActivity.this, R.string.sign_up_successful, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onErrorRequest(final String errorMessage) {
                        dismissProgressDialog();
                        showDialog(R.string.sign_up, errorMessage);
                    }

                }).execute();

    }
}
