package com.alpaka.foursquareconnectsample.restful.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
public class VenuePhoto implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("prefix")
    public String prefix;

    @SerializedName("suffix")
    public String suffix;


}
