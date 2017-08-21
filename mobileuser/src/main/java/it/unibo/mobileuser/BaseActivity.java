package it.unibo.mobileuser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity from which every activity of the App extends
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Sets the fragment on the view indicated by containerId.
     *
     * @param fragment    fragment to set
     * @param containerId layout resource in which to put the fragment
     * @param addBack     if true add the transaction to the back stack
     */
    protected void setFragment(final Fragment fragment, final int containerId, final boolean addBack) {
        setFragment(fragment, containerId, fragment.getClass().getName(), addBack);
    }

    /**
     * Sets the fragment on the view indicated by containerId.
     *
     * @param fragment    fragment to set
     * @param containerId layout resource in which to put the fragment
     * @param tag         tag to identify the fragment on the activity
     * @param addBack     if true add the transaction to the back stack
     */
    private void setFragment(final Fragment fragment, final int containerId, final String tag, final boolean addBack) {
        if (isFinishing())
            return;
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        if (addBack)
            transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

}
