package it.unibo.mobileuser;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity from which every activity of the App extends.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Shows a simple dialog with a title and a message.
     *
     * @param title   title of dialog
     * @param message message of dialog
     */
    protected void showDialog(final int title, final String message) {
        new AlertDialog.Builder(this, R.style.alert_dialog_theme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    /**
     * Shows a simple dialog with a title and a message.
     *
     * @param title   title of dialog
     * @param message message of dialog
     */
    protected final void showDialog(final int title, final int message) {
        showDialog(title, getResources().getString(message));
    }
}
