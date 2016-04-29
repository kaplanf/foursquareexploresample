package com.alpaka.foursquareconnectsample.restful.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
public class Venue implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("location")
    public Location location;

    @SerializedName("venuePhoto")
    public VenuePhoto venuePhoto;

    public class Location {

        @SerializedName("address")
        public String address;

        @SerializedName("crossStreet")
        public String crossStreet;

        @SerializedName("lat")
        public double lat;

        @SerializedName("lng")
        public double lng;

        @SerializedName("distance")
        public int distance;


    }
}
