package com.alpaka.foursquareconnectsample.restful.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
public class ResponseObject implements Serializable {

    @SerializedName("response")
    public Response response;


    public class Response implements Serializable {
        @SerializedName("groups")
        public ArrayList<Group> groups;
    }

    public class Group implements Serializable {
        @SerializedName("items")
        public ArrayList<Item> items;
    }

    public class Item implements Serializable {
        @SerializedName("venue")
        public Venue venue;
    }
}
