package com.alpaka.foursquareconnectsample;

import com.alpaka.foursquareconnectsample.activities.BaseActivity;
import com.alpaka.foursquareconnectsample.fragments.MainFragment;
import com.alpaka.foursquareconnectsample.fragments.MainFragment_;
import com.alpaka.foursquareconnectsample.fragments.VenueListFragment;
import com.alpaka.foursquareconnectsample.fragments.VenueListFragment_;
import com.alpaka.foursquareconnectsample.fragments.VenueMapFragment;
import com.alpaka.foursquareconnectsample.fragments.VenueMapFragment_;
import com.alpaka.foursquareconnectsample.interfaces.OnMainFragmentListener;
import com.alpaka.foursquareconnectsample.restful.model.Venue;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnMainFragmentListener {


    @AfterViews
    protected void afterViews() {
        toMainFragment();
    }


    public void toMainFragment() {
        MainFragment mainFragment = new MainFragment_();
        replaceFragment(R.id.main_frame, mainFragment, false);
    }

    public void toMapVenueFragment(ArrayList<LatLng> latLngs) {
        VenueMapFragment venueMapFragment = VenueMapFragment_.builder().latLngArrayList(latLngs).build();
        replaceFragment(R.id.main_frame, venueMapFragment, true);
    }

    public void toListVenueFragment(ArrayList<String> ids, ArrayList<Venue> venues) {
        VenueListFragment fragment = VenueListFragment_.builder().idList(ids).venueList(venues).build();
        replaceFragment(R.id.main_frame, fragment, true );
    }

    @Override
    public void onCloseFragment(String tag) {

    }

    @Override
    public void onStartFragment(String tag) {

    }
}
