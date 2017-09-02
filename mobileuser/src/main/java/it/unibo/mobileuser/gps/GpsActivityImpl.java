package it.unibo.mobileuser.gps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import it.unibo.mobileuser.R;
import it.unibo.mobileuser.ToolbarActivity;

/**
 * Activity that allows to use GPS of device to find latitude and longitude and check permissions.
 */
public class GpsActivityImpl extends ToolbarActivity implements GpsActivity {

    private static final int REQUEST_CODE = 200;
    private static final int MIN_INTERVAL = 5000;
    private static final int MIN_DISTANCE = 1;
    private LocationManager locationManager;
    private Location location;
    private String latitude;
    private String longitude;
    private GpsListener listener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        this.listener = new GpsListener(this);
        checkPermission();
        checkProvider();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationManager.removeUpdates(this.listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    public String getLatitude() {
        return this.latitude;
    }

    @Override
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getLongitude() {
        return this.longitude;
    }

    @Override
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission();
                checkProvider();
            } else {
                Toast.makeText(this, getResources().getString(R.string.gps_permission), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Verify whether the user has given permission to use the GPS. If he did not, permission is required,
     * otherwise geographic location is taken.
     */
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        } else {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_INTERVAL, MIN_DISTANCE, this.listener);
            if (this.locationManager != null) {
                this.location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
    }

    /**
     * Verify whether the GPS is active. If it is active, latitude and longitude values are found, otherwise an alert
     * is displayed.
     */
    private void checkProvider() {
        if (this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            this.listener.onLocationChanged(this.location);
        } else {
            showGPSAlert();
        }
    }

    /**
     * Show an alert to inform the user who must turn on the GPS to use the application.
     */
    protected void showGPSAlert() {
        final AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.attention))
                .setMessage(getResources().getString(R.string.gps_alert_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.gps_alert_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.alert_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        dialogInterface.cancel();
                    }
                })
                .create();
        alert.show();
    }


}
