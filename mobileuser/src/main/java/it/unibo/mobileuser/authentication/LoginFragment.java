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
import it.unibo.mobileuser.R;

/**
 * Fragment to login in the mobileuser App
 */
public class LoginFragment extends Fragment {

    private LoginListener listener;
    private Button loginButton;
    private MaterialEditText emailEditText;
    private MaterialEditText passwordEditText;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.emailEditText = view.findViewById(R.id.email_login);
        this.passwordEditText = view.findViewById(R.id.password_login);

        this.loginButton = view.findViewById(R.id.login);
        this.loginButton.setOnClickListener((v) -> {
            if (LoginFragment.this.listener != null) {
                //TODO check on editText
                //LoginFragment.this.listener.signIn();
            }
        });

        return view;

    }

    /**
     * Set the activity listener to the listener implemented by this fragment
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
