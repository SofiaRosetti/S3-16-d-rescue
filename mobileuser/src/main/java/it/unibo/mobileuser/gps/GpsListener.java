package it.unibo.mobileuser.gps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import it.unibo.mobileuser.R;

/**
 * Custom listener to capture position change and GPS activation.
 */
public class GpsListener implements LocationListener {

    private final GpsActivityImpl gpsActivity;

    /**
     * Constructor.
     *
     * @param gpsActivity the activity that uses the listener.
     */
    public GpsListener(final GpsActivityImpl gpsActivity) {
        this.gpsActivity = gpsActivity;
    }

    @Override
    public void onLocationChanged(final Location location) {
        if (location != null) {
            setCoordinates(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        } else {
            setCoordinates(this.gpsActivity.getResources().getString(R.string.location_not_available));
        }
    }

    @Override
    public void onStatusChanged(final String s, final int i, final Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(final String provider) {

    }

    @Override
    public void onProviderDisabled(final String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            this.gpsActivity.showGPSAlert();
        }
    }

    /**
     * Set the latitude and longitude value in the activity.
     *
     * @param latitudeValue  latitude
     * @param longitudeValue longitude
     */
    private void setCoordinates(final String latitudeValue, final String longitudeValue) {
        this.gpsActivity.setLatitude(latitudeValue);
        this.gpsActivity.setLongitude(longitudeValue);
    }

    /**
     * Set the latitude and longitude value when only one default value is available.
     *
     * @param defaultValue default value for latitude and longitude.
     */
    private void setCoordinates(final String defaultValue) {
        setCoordinates(defaultValue, defaultValue);
    }
}
