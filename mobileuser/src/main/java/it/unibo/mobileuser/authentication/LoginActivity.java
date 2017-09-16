package it.unibo.mobileuser.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.rengwuxian.materialedittext.MaterialEditText;
import it.unibo.drescue.StringUtils;
import it.unibo.drescue.communication.GsonUtils;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.communication.messages.MessageUtils;
import it.unibo.drescue.communication.messages.requests.LoginMessageImpl;
import it.unibo.drescue.communication.messages.response.ResponseLoginMessageImpl;
import it.unibo.drescue.connection.QueueType;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.UserImpl;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;
import it.unibo.mobileuser.connection.AbstractResponse;
import it.unibo.mobileuser.connection.RabbitAsyncTask;
import it.unibo.mobileuser.utils.PreferencesKey;
import it.unibo.mobileuser.utils.Utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
                final Message message = new LoginMessageImpl(email, password);
                login(message);
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

        showProgressDialog();

        new RabbitAsyncTask(QueueType.MOBILEUSER_QUEUE.getQueueName(),
                message,
                new AbstractResponse() {
                    @Override
                    public void onSuccessfulRequest(final String response) {
                        dismissProgressDialog();
                        if (MessageUtils.getMessageNameByJson(response) == MessageType.RESPONSE_LOGIN_MESSAGE) {
                            final ResponseLoginMessageImpl loginMessage = GsonUtils.fromGson(response, ResponseLoginMessageImpl.class);
                            final UserImpl user = loginMessage.getUser();
                            setSharedPreferences(PreferencesKey.USER_ID, Collections.singleton(Integer.toString(user.getUserID())));
                            setSharedPreferences(PreferencesKey.USER_NAME, Collections.singleton(user.getName()));
                            setSharedPreferences(PreferencesKey.USER_SURNAME, Collections.singleton(user.getSurname()));
                            setSharedPreferences(PreferencesKey.USER_EMAIL, Collections.singleton(user.getEmail()));
                            setSharedPreferences(PreferencesKey.USER_PHONE, Collections.singleton(user.getPhoneNumber()));
                            final Set<String> eventTypeSet = new HashSet<>();
                            for (final EventType event : loginMessage.getEventsType()) {
                                eventTypeSet.add(event.getEventName());
                            }
                            setSharedPreferences(PreferencesKey.EVENT_TYPE, eventTypeSet);
                            finish();
                        }
                    }

                    @Override
                    public void onErrorRequest(final String errorMessage) {
                        dismissProgressDialog();
                        showDialog(R.string.login, errorMessage);
                    }
                }).execute();
    }

    /**
     * Sets the shared preferences.
     *
     * @param key      the name of preference
     * @param setValue a set of values for preferences
     */
    private void setSharedPreferences(final PreferencesKey key, final Set<String> setValue) {
        Utils.setDataInSharedPreferences(getApplicationContext(), key, setValue);
    }
}
