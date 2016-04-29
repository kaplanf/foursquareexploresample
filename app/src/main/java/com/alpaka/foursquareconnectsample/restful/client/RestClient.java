package com.alpaka.foursquareconnectsample.restful.client;

import com.alpaka.foursquareconnectsample.restful.model.PhotoResponseObject;
import com.alpaka.foursquareconnectsample.restful.model.ResponseObject;
import com.alpaka.foursquareconnectsample.util.NetworkConstants;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by Fatih Kaplan on 28/04/16.
 */
@Rest(rootUrl = NetworkConstants.BASE_URL, converters = {GsonHttpMessageConverter.class, StringHttpMessageConverter.class})
public interface RestClient extends RestClientHeaders {

    @Get(NetworkConstants.SEARCH_VENUES)
    ResponseObject getVenues(@Path String ll, @Path String client_id, @Path String client_secret, @Path String v, @Path String limit, @Path String section);

    @Get(NetworkConstants.VENUE_PHOTOS)
    PhotoResponseObject getVenuePhotos(@Path String venue_id, @Path String client_id, @Path String client_secret, @Path String v, @Path String limit);
}
