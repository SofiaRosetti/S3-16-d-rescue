package it.unibo.mobileuser;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Activity from which other activities extends to set toolbar.
 */
public class ToolbarActivity extends BaseActivity {

    /**
     * Set the toolbar in place of ActionBar.
     *
     * @param isVisible true if the toolbar must be visibile, otherwise false.
     */
    public void setToolbar(final boolean isVisible) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbarVisibility(isVisible);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Set the visibility of the toolbar inside an activity.
     *
     * @param visible if true the toolbar is visible inside the activity
     */
    protected void setToolbarVisibility(final boolean visible) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
            getSupportActionBar().setHomeButtonEnabled(visible);
            getSupportActionBar().setDisplayShowTitleEnabled(visible);
        }
    }
}
