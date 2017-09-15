package it.unibo.mobileuser;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Base activity from which every activity of the App extends.
 */
public class BaseActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean isDoingRequest = false;

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

    /**
     * Creates and shows a progress dialog for needs of download data from server.
     */
    protected void showProgressDialog() {
        final LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.progress_bar, null);
        this.dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        this.dialog.show();
    }

    /**
     * Dismisses progress dialog when download is terminate.
     */
    protected void dismissProgressDialog() {
        this.dialog.dismiss();
    }

    /**
     * Sets true if needs to wait for download, otherwise false.
     */
    protected void setDoingRequest() {
        this.isDoingRequest = !this.isDoingRequest;
    }

    @Override
    public void onBackPressed() {
        if (!this.isDoingRequest) {
            super.onBackPressed();
        }
    }
}
