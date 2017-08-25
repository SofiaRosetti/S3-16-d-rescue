package it.unibo.mobileuser;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity from which every activity of the App extends.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Shows a simple dialog with a title and a message
     *
     * @param title
     * @param message
     */
    protected final void showDialog(final int title, final int message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
