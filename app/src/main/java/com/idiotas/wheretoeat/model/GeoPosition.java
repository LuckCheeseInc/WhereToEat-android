package com.idiotas.wheretoeat.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class GeoPosition {

    private double lat;
    private double lng;

    public static class Bounds {
        private GeoPosition ne;
        private GeoPosition sw;
    }

    public static GeoPosition fromLocation(Location location) {
        if (location != null) {
            GeoPosition position = new GeoPosition();
            position.lat = location.getLatitude();
            position.lng = location.getLongitude();
            return position;
        }
        else {
            return null;
        }
    }

    public static LatLng latLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

//    public LatLng toLatLng() {
//        return new LatLng(lat, lng);
//    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
