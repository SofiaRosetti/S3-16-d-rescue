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
                        if (SignInFragment.this.listener != null) {
                            SignInFragment.this.listener.signIn(name, surname, email, phone, password);
                        }
                    } else {
                        //TODO show dialog "password and confirm password do not match"
                    }
                } else {
                    //TODO show dialog "incorrect email format"
                }
            } else {
                //TODO show dialog "all fields must be filled"
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
