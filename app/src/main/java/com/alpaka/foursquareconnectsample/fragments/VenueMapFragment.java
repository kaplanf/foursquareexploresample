package com.alpaka.foursquareconnectsample.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.alpaka.foursquareconnectsample.R;
import com.alpaka.foursquareconnectsample.location.LocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
@EFragment(R.layout.venue_map_fragment)
public class VenueMapFragment extends BaseFragment implements OnMapReadyCallback {

    @FragmentArg
    ArrayList<LatLng> latLngArrayList;

    @ViewById(R.id.locationFrameLayout)
    FrameLayout locationFrameLayout;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    private LocationManager locationManager;

    private LatLngBounds bounds;

    private int latlngSize;

    private LatLngBounds.Builder builder;

    @AfterViews
    protected void afterViews() {
        if (latLngArrayList != null) {
            latlngSize = latLngArrayList.size();
        }

        initGoogleMap();
    }

    private void initGoogleMap() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (status == ConnectionResult.SUCCESS) {
            mapFragment = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.locationFrameLayout, mapFragment, MapFragment.class.getSimpleName());
            fragmentTransaction.commit();
            mapFragment.getMapAsync(this);
        } else GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        locationManager = LocationManager.getInstance();
        LatLng myPosition = locationManager.getMyPosition();

        builder = new LatLngBounds.Builder();

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int a = 0; a < latlngSize; a++) {
            builder.include(latLngArrayList.get(a));

            View itemLayout = layoutInflater.inflate(R.layout.extend_map_item_marker, null, false);
            IconGenerator itemIconGenerator = new IconGenerator(getActivity());
            itemIconGenerator.setContentView(itemLayout);
            itemIconGenerator.setBackground(getResources().getDrawable(android.R.color.transparent));
            Bitmap bitmap = itemIconGenerator.makeIcon();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            markerOptions.position(latLngArrayList.get(a));
            googleMap.addMarker(markerOptions);
        }

        bounds = builder.include(myPosition).build();


        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

            }
        });
    }

}