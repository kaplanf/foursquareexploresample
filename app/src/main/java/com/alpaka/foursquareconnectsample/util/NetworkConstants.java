package com.alpaka.foursquareconnectsample.util;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
public class NetworkConstants {
    public static final String BASE_URL = "https://api.foursquare.com/v2/";

    public static final String SEARCH_VENUES = "venues/explore?ll={ll}&client_id={client_id}&client_secret={client_secret}&v={v}&limit={limit}&section={section}";

    public static final String VENUE_PHOTOS = "venues/{venue_id}/photos?client_id={client_id}&client_secret={client_secret}&v={v}&limit={limit}";

}
