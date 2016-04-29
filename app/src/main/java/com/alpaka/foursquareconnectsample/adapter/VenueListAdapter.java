package com.alpaka.foursquareconnectsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpaka.foursquareconnectsample.FoursquareConnectApplication;
import com.alpaka.foursquareconnectsample.R;
import com.alpaka.foursquareconnectsample.restful.model.Venue;

import java.util.ArrayList;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
public class VenueListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Venue> venueArrayList;
    private Context mContext;

    public VenueListAdapter(Context context, ArrayList<Venue> venues) {
        mContext = context;
        venueArrayList = venues;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return venueArrayList.size();
    }

    @Override
    public Venue getItem(int position) {
        return venueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View vi = view;
        ViewHolder holder = null;
        if (vi == null) {
            vi = inflater.inflate(R.layout.venue_list_item, parent, false);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.venueItemTitle);
            holder.address = (TextView) vi.findViewById(R.id.venueItemAddress);
            holder.image = (ImageView) vi.findViewById(R.id.venueItemImageView);


            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        final Venue venue = getItem(position);

        if (venue != null) {

            holder.title.setText(venue.name);
            holder.address.setText(venue.location.address);
            if (venue.venuePhoto != null) {
                FoursquareConnectApplication.getInstance().p.with(mContext).
                        load(venue.venuePhoto.prefix + "700x700" + venue.venuePhoto.suffix).into(holder.image);
            }
        }
        return vi;

    }


    static class ViewHolder {

        TextView title;

        TextView address;

        ImageView image;
    }
}
