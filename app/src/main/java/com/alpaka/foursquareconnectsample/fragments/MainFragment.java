package com.alpaka.foursquareconnectsample.fragments;

import android.content.Context;
import android.location.Location;
import android.widget.TextView;

import com.alpaka.foursquareconnectsample.FoursquareConnectApplication;
import com.alpaka.foursquareconnectsample.MainActivity;
import com.alpaka.foursquareconnectsample.R;
import com.alpaka.foursquareconnectsample.interfaces.OnMainFragmentListener;
import com.alpaka.foursquareconnectsample.location.LocationManager;
import com.alpaka.foursquareconnectsample.restful.client.RestClient;
import com.alpaka.foursquareconnectsample.restful.model.ResponseObject;
import com.alpaka.foursquareconnectsample.restful.model.Venue;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
@EFragment(R.layout.main_fragment)
public class MainFragment extends BaseFragment implements LocationManager.LocationUpdatesListener {

    @RestService
    RestClient restClient;

    @ViewById(R.id.listVenueDisplayText)
    TextView listVenueDisplayText;

    @ViewById(R.id.mapVenueDisplayText)
    TextView mapVenueDisplayText;

    OnMainFragmentListener mainFragmentListener;

    private ResponseObject responseObject;

    LocationManager locationManager;

    private boolean isLocationAcquired = false;

    ArrayList<String> ids;

    ArrayList<Venue> venues;

    ArrayList<LatLng> latLngArrayList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainFragmentListener = (OnMainFragmentListener) context;
        locationManager = LocationManager.getInstance();
        locationManager.setLocationUpdatesListener(this);
    }

    @AfterViews
    protected void afterViews() {
        Location location = locationManager.getLastLocation();
        if (location != null) {
            System.out.println("location acquired from initializer");
            isLocationAcquired = true;
            getVenues();
        }
    }

    @Background
    void getVenues() {
        Location location = locationManager.getLastLocation();
        String ll = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
        responseObject = restClient.
                getVenues(ll, FoursquareConnectApplication.getInstance().FQ_APP_ID,
                        FoursquareConnectApplication.getInstance().FQ_APP_SECRET,
                        FoursquareConnectApplication.getInstance().getDate(), "10", "food");

        if (responseObject != null) {
            int size = responseObject.response.groups.get(0).items.size();
            ids = new ArrayList<String>();
            venues = new ArrayList<Venue>();
            latLngArrayList = new ArrayList<LatLng>();
            for (int a = 0; a < size; a++) {
                ids.add(responseObject.response.groups.get(0).items.get(a).venue.id);
                venues.add(responseObject.response.groups.get(0).items.get(a).venue);
                latLngArrayList.add(new LatLng(responseObject.response.groups.get(0).items.get(a).venue.location.lat,
                        responseObject.response.groups.get(0).items.get(a).venue.location.lng));
            }
        }
        hideProgressDialog();
    }


    @Click
    void listVenueDisplayText() {
        ((MainActivity) getActivity()).toListVenueFragment(ids, venues);
    }

    @Click
    void mapVenueDisplayText() {
        ((MainActivity) getActivity()).toMapVenueFragment(latLngArrayList);
    }

    @Override
    public void sendLocation(Location location) {
        if (!isLocationAcquired) {
            System.out.println("location acquired from listener");
            showProgressDialog();
            getVenues();
        }
    }
}
