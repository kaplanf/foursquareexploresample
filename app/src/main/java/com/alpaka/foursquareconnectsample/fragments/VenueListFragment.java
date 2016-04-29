package com.alpaka.foursquareconnectsample.fragments;

import android.content.Context;
import android.widget.ListView;

import com.alpaka.foursquareconnectsample.FoursquareConnectApplication;
import com.alpaka.foursquareconnectsample.R;
import com.alpaka.foursquareconnectsample.adapter.VenueListAdapter;
import com.alpaka.foursquareconnectsample.restful.client.RestClient;
import com.alpaka.foursquareconnectsample.restful.model.PhotoResponseObject;
import com.alpaka.foursquareconnectsample.restful.model.Venue;
import com.alpaka.foursquareconnectsample.restful.model.VenuePhoto;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
@EFragment(R.layout.venue_list_fragment)
public class VenueListFragment extends BaseFragment {

    @RestService
    RestClient restClient;

    @FragmentArg
    ArrayList<Venue> venueList;

    @FragmentArg
    ArrayList<String> idList;

    @ViewById(R.id.venueListview)
    ListView venueListview;

    VenueListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @AfterViews
    protected void afterViews() {
        adapter = new VenueListAdapter(getActivity(), venueList);
        venueListview.setAdapter(adapter);
        getPhotos(idList);
    }

    @Background
    void getPhotos(ArrayList<String> idList) {
        int size = idList.size();
        ArrayList<ArrayList<VenuePhoto>> venuePhotosArrayList = new ArrayList<ArrayList<VenuePhoto>>();
        for (int a = 0; a < size; a++) {
            PhotoResponseObject photoResponseObject = new PhotoResponseObject();
            photoResponseObject = restClient.getVenuePhotos(idList.get(a),
                    FoursquareConnectApplication.getInstance().FQ_APP_ID,
                    FoursquareConnectApplication.getInstance().FQ_APP_SECRET,
                    FoursquareConnectApplication.getInstance().getDate(), "1");
            venuePhotosArrayList.add(photoResponseObject.response.photos.items);
        }
        for (int a = 0; a < size; a++) {
            venueList.get(a).venuePhoto = venuePhotosArrayList.get(a).get(0);
        }
        System.out.println("testPhotosRetrieved");
        updateListView();
    }

    @UiThread
    void updateListView() {
        adapter.notifyDataSetChanged();
    }
}
