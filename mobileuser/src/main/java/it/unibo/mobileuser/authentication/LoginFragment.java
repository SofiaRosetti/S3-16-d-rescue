package it.unibo.mobileuser.authentication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.rengwuxian.materialedittext.MaterialEditText;
import it.unibo.drescue.StringUtils;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.utils.Utils;

/**
 * Fragment displayed to login in the app.
 */
public class LoginFragment extends Fragment {

    private LoginListener listener;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        final MaterialEditText emailEditText = view.findViewById(R.id.email_login);
        final MaterialEditText passwordEditText = view.findViewById(R.id.password_login);

        final Button loginButton = view.findViewById(R.id.login);
        loginButton.setOnClickListener((v) -> {

            final String email = Utils.getEditTextString(emailEditText);
            final String password = Utils.getEditTextString(passwordEditText);
            if (StringUtils.isAValidString(email) && StringUtils.isAValidString(password) &&
                    StringUtils.isAValidEmail(email)) {
                if (LoginFragment.this.listener != null) {
                    LoginFragment.this.listener.login(email, password);
                }
            } else {
                //TODO show error dialog "incorrect login data"
            }
        });

        return view;

    }

    /**
     * Set the activity listener to the listener implemented by this fragment.
     *
     * @param listener
     */
    private void setListener(final LoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LoginListener) {
            setListener((LoginListener) activity);
        }
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }
}
