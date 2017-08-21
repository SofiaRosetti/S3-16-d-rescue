package it.unibo.mobileuser.authentication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import it.unibo.mobileuser.R;

public class SplashFragment extends Fragment {

    private SplashListener listener;
    private Button loginButton;
    private TextView signInTextView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_splash, container, false);

        this.loginButton = view.findViewById(R.id.splash_login);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (SplashFragment.this.listener != null) {
                    System.out.println("[SplashFragment] Login pressed!");
                    //listener.onRequestLogin();
                }
            }
        });

        this.signInTextView = view.findViewById(R.id.splash_sign_in);
        this.signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (SplashFragment.this.listener != null) {
                    System.out.println("[SplashFragment] Sign in pressed!");
                    //listener.onRequestSignIn();
                }
            }
        });

        return view;
    }

    private void setListener(final SplashListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SplashListener) {
            setListener((SplashListener) activity);
        }
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }
}
