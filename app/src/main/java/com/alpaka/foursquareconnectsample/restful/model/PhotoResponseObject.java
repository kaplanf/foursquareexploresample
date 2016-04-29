package com.alpaka.foursquareconnectsample.restful.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fatih Kaplan on 29/04/16.
 */
public class PhotoResponseObject implements Serializable {

    @SerializedName("response")
    public Response response;

    public class Response {
        @SerializedName("photos")
        public Photos photos;
    }

    public class Photos {
        @SerializedName("items")
        public ArrayList<VenuePhoto> items;
    }

}
