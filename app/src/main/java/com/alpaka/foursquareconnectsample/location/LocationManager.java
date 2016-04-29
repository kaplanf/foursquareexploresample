package com.alpaka.foursquareconnectsample.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final float INVALID_DISTANCE = -1f;

    private static LocationManager instance;
    private static GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location lastLocation;
    private Context context;

    private LocationManager() {
    }

    public static synchronized LocationManager getInstance() {
        return instance;
    }

    private LocationUpdatesListener listener;

    public interface LocationUpdatesListener {
        void sendLocation(Location location);
    }

    public void setLocationUpdatesListener(LocationUpdatesListener l) {
        listener = l;
    }

    public static synchronized void initializeManager(Context context) {
        if (instance == null) instance = new LocationManager();
        instance.context = context;
        mGoogleApiClient =
                new GoogleApiClient.Builder(context).addConnectionCallbacks(instance).addOnConnectionFailedListener(instance).addApi(LocationServices.API)
                        .build();
        mGoogleApiClient.connect();
    }

    public double computeDistanceBetween(LatLng from, LatLng to) {
        return SphericalUtil.computeDistanceBetween(from, to);
    }

    public double computeDistanceFromMe(LatLng to) {
        LatLng currentPosition = getMyPosition();
        if (currentPosition != null)
            return SphericalUtil.computeDistanceBetween(currentPosition, to);
        else return 0;
    }

    public String getDistanceFromMe(LatLng to) {
        LatLng currentPosition = getMyPosition();
        if (currentPosition != null) {
            return formatKm(SphericalUtil.computeDistanceBetween(currentPosition, to));
        } else {
            return null;
        }
    }

    private String formatMile(double distance) {
        return String.format("%4.1f%s", distance / 1609.344d, "ml");
    }

    private String formatKm(double distance) {
        return String.format("%4.1f%s", distance / 1000, "km");
    }

    public LatLng getMyPosition() {
        if (lastLocation != null)
            return new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        else return null;
    }

    public Location getLastLocation() {
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleApiClient.disconnect();
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest request = new LocationRequest();
        request.setInterval(10 * 1000); // 10 seconds
        request.setFastestInterval(10 * 1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return request;
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (listener != null) {
                listener.sendLocation(lastLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lastLocation = null;
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//            mActivity.onGooglePlayServicesError(this, connectionResult);
    }

    protected void startLocationUpdates() {
        if (mLocationRequest == null) {
            mLocationRequest = createLocationRequest();
        }
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            lastLocation = location;
            if (listener != null) {
                listener.sendLocation(lastLocation);
            }
        }
    }

    public float getDistanceToPoint(double latitude, double longitude) {
        if (lastLocation == null) {
            return INVALID_DISTANCE;
        }
        Location loc = new Location("");
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        return lastLocation.distanceTo(loc);
    }
}
