package com.idiotas.wheretoeat.model.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.idiotas.wheretoeat.model.GeoPosition;

public class GeoPositionManager implements LocationListener {

    private static GeoPositionManager instance;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    private LocationManager locationManager;
    private GeoLocationListener listener;

    public static GeoPositionManager getInstance(GeoLocationListener listener) {
        if (instance == null) {
            instance = new GeoPositionManager();
        }

        instance.listener = listener;
        return instance;
    }

    private GeoPositionManager() {

    }

    public void start(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (isEnabled()) {
            startProvider(LocationManager.NETWORK_PROVIDER);
            startProvider(LocationManager.GPS_PROVIDER);
        }
        else {
            if (listener != null) {
                listener.serviceNotEnabled();
            }
        }
    }

    public void stop() {
        if (locationManager == null) {
            return;
        }

        locationManager.removeUpdates(this);
        locationManager = null;
    }

    public boolean isEnabled() {
        return isGpsEnabled() || isNetworkEnabled();
    }

    public boolean isGpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isNetworkEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void startProvider(String provider) {
        locationManager.requestLocationUpdates(
                provider,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    public GeoPosition getPosition() {
        Location location = null;
        if (isNetworkEnabled()) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else if (isGpsEnabled()) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        return GeoPosition.fromLocation(location);
    }

    // ----- LocationListener -------------------------------------------------

    @Override
    public void onLocationChanged(Location location) {
        if (listener != null) {
            listener.setNewLocation(GeoPosition.fromLocation(location));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        startProvider(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (!isEnabled() && listener != null) {
            listener.serviceNotEnabled();
        }
    }

    // ----- Listener ---------------------------------------------------------

    public interface GeoLocationListener {
        void serviceNotEnabled();
        void setNewLocation(GeoPosition position);
    }
}
