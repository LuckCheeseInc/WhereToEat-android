package com.idiotas.wheretoeat;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idiotas.wheretoeat.model.FourSquareResponse;
import com.idiotas.wheretoeat.model.GeoPosition;
import com.idiotas.wheretoeat.model.app.FourSquareManager;


public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        View.OnClickListener,
        GoogleMap.OnMyLocationChangeListener,
        GoogleMap.OnMyLocationButtonClickListener,
        FourSquareManager.Listener
{

    private static final int ZOOM_ON_CURRENT_LOCATION = 20;
    private static final int PADDING_ON_SHOWING_VENUW = 200;

    private GoogleMap map;
    private FourSquareResponse.GroupItem showingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.refreshBtn).setOnClickListener(this);
    }

    private void updatePosAndRefresh() {
        Location location = map.getMyLocation();
        if (location != null) {
            centerMapOnUser(location);
            refreshChosenRestaurant(GeoPosition.fromLocation(location));
        }
        else {
            map.setOnMyLocationChangeListener(this);
        }
    }

    private void refreshChosenRestaurant(GeoPosition postion) {
        FourSquareManager.getInstance().getChosenRestaurant(this, postion);
    }

    private void showVenueOnScreen(FourSquareResponse.GroupItem item) {
        showingItem = item;
        FourSquareResponse.Venue venue = item.getVenue();

        LatLng venueLatLng = new LatLng(venue.getLocation().getLat(), venue.getLocation().getLng());
        MarkerOptions marker = new MarkerOptions()
                .position(venueLatLng)
                .title(venue.getName());
        map.addMarker(marker);

        centerMapOnUser(map.getMyLocation());
    }

    private void centerMapOnUser(Location location) {
        LatLng userLocation = GeoPosition.latLngFromLocation(location);

        if (showingItem != null) {
            FourSquareResponse.Venue venue = showingItem.getVenue();
            LatLng venueLocation = new LatLng(venue.getLocation().getLat(), venue.getLocation().getLng());

            LatLngBounds bounds = LatLngBounds
                    .builder()
                    .include(userLocation)
                    .include(venueLocation)
                    .build();
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, PADDING_ON_SHOWING_VENUW));
        }
        else {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, ZOOM_ON_CURRENT_LOCATION));
        }
    }

    // ----- OnMapReadyCallback -----------------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        updatePosAndRefresh();
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.refreshBtn) {
            updatePosAndRefresh();
        }
    }

    // ----- GoogleMap.OnMyLocationChangeListener -----------------------------

    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            centerMapOnUser(location);
            refreshChosenRestaurant(GeoPosition.fromLocation(location));
            map.setOnMyLocationChangeListener(null);
        }
    }

    // ----- GoogleMap.OnMyLocationButtonClickListener ------------------------

    @Override
    public boolean onMyLocationButtonClick() {
        centerMapOnUser(map.getMyLocation());
        return true;
    }

    // ----- FourSquareManager.Listener ---------------------------------------

    @Override
    public void setChosenRestaurant(FourSquareResponse.GroupItem item) {
        showVenueOnScreen(item);
    }
}
