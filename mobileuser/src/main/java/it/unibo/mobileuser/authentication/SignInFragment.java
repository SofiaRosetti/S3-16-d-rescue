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
 * Fragment to sign in in the mobileuser App.
 */
public class SignInFragment extends Fragment {

    private SignInListener listener;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        final MaterialEditText nameEditText = view.findViewById(R.id.name_sign_in);
        final MaterialEditText surnameEditText = view.findViewById(R.id.surname_sign_in);
        final MaterialEditText emailEditText = view.findViewById(R.id.email_sign_in);
        final MaterialEditText phoneEditText = view.findViewById(R.id.phone_sign_in);
        final MaterialEditText passwordEditText = view.findViewById(R.id.password_sign_in);
        final MaterialEditText confirmPasswordEditText = view.findViewById(R.id.confirm_password_sign_in);

        final Button signInButton = view.findViewById(R.id.sign_in);
        signInButton.setOnClickListener((v) -> {
            if (SignInFragment.this.listener != null) {
                //TODO check on editText
                //SignInFragment.this.listener.signIn();
            }
        });

        return view;
    }

    /**
     * Set the activity listener to the listener implemented by this fragment.
     *
     * @param listener
     */
    private void setListener(final SignInListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SignInListener) {
            setListener((SignInListener) activity);
        }
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }

}
