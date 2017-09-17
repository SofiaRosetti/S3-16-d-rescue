package it.unibo.mobileuser.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.builder.requests.ChangePasswordMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.connection.QueueType;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
import it.unibo.mobileuser.connection.AbstractResponse;
import it.unibo.mobileuser.connection.RabbitAsyncTask;
import it.unibo.mobileuser.utils.PreferencesKey;
import it.unibo.mobileuser.utils.Utils;

/**
 * A class that allows to show the graphical interface to change the password of account and save the changes on server.
 */
public class ChangePasswordActivity extends ToolbarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setToolbar(true);
        getSupportActionBar().setTitle(R.string.change_password);

        final MaterialEditText oldPassword = (MaterialEditText) findViewById(R.id.old_password);
        final MaterialEditText newPassword = (MaterialEditText) findViewById(R.id.new_password);
        final MaterialEditText newPasswordConfirm = (MaterialEditText) findViewById(R.id.new_password_confirm);

        final Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener((View view) -> {
            final String oldPasswordText = Utils.getEditTextString(oldPassword);
            final String newPasswordText = Utils.getEditTextString(newPassword);
            final String newPasswordConfirmText = Utils.getEditTextString(newPasswordConfirm);
            if (StringUtils.isAValidString(oldPasswordText) && StringUtils.isAValidString(newPasswordText)
                    && StringUtils.isAValidString(newPasswordConfirmText)) {
                if (StringUtils.isAValidPassword(oldPasswordText) && StringUtils.isAValidPassword(newPasswordText)) {
                    if (newPasswordText.equals(newPasswordConfirmText)) {
                        final Message message = new ChangePasswordMessageBuilderImpl()
                                .setUserEmail(Utils.getUserDataFromSharedPreferences(getApplicationContext(), PreferencesKey.USER_EMAIL))
                                .setOldPassword(oldPasswordText)
                                .setNewPassword(newPasswordText)
                                .build();
                        changePassword(message);
                    } else {
                        showDialog(R.string.change_password, R.string.password_mismatch);
                    }
                } else {
                    showDialog(R.string.change_password, R.string.incorrect_password_format);
                }
            } else {
                showDialog(R.string.change_password, R.string.fill_fields);
            }

        });
    }

    /**
     * Performs change password request with the given message.
     *
     * @param message message containing user data to change password
     */
    private void changePassword(final Message message) {
        showProgressDialog();
        new RabbitAsyncTask(QueueType.MOBILEUSER_QUEUE.getQueueName(),
                message,
                new AbstractResponse() {
                    @Override
                    public void onSuccessfulRequest(final String response) {
                        dismissProgressDialog();
                        Toast.makeText(ChangePasswordActivity.this, R.string.change_password_successful, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onErrorRequest(final String errorMessage) {
                        dismissProgressDialog();
                        showDialog(R.string.change_password, errorMessage);
                    }
                }).execute();
    }
}
