package it.unibo.mobileuser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void setFragment(Fragment fragment, int containerId, boolean addBack) {
        setFragment(fragment, containerId, fragment.getClass().getName(), addBack);
    }

    private void setFragment(Fragment fragment, int containerId, String tag, boolean addBack) {
        if (isFinishing())
            return;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        if (addBack)
            transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

}
