package com.github.kulebin.myfoursquareapp.api;

import android.net.Uri;

import com.github.kulebin.myfoursquareapp.http.HttpRequest;
import com.github.kulebin.myfoursquareapp.http.HttpRequestType;

public final class Api {

    private static final String SCHEME = "https://";
    public static final String AUTHORITY = "api.foursquare.com";
    private static final String BASE_URL = SCHEME + AUTHORITY;
    private static final String API_VERSION = "v2";

    private static final String PATH_VENUES = "venues";
    private static final String PATH_TRENDING = "trending";
    private static final String PATH_NEARBY = "nearby";

    private static final String PARAM_DATE_VERIFIED = "v";
    private static final String PARAM_LATITUDE_AND_LONGITUDE = "ll";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_RADIUS = "radius";
    private static final String PARAM_TEMPLATE = "%s=%s";
    private static final String PARAM_SEPARATOR = "&";

    //temporarily hardcoded values
    private static final String VALUE_DATE_VERIFIED = "20170226"; /*Date value format: YYYYMMDD */
    private static final String VALUE_LATITUDE_AND_LONGITUDE_MINSK = "40.748817,-73.985428";// "53.9,27.56667";

    private static final Uri VENUE_BASE_URI = Uri.parse(Api.BASE_URL)
            .buildUpon()
            .appendPath(API_VERSION)
            .appendPath(PATH_VENUES)
            .build();

    public static String getVenuesTrendingUrl() {
        return VENUE_BASE_URI.buildUpon()
                .appendPath(PATH_TRENDING)
                .appendQueryParameter(PARAM_LATITUDE_AND_LONGITUDE, VALUE_LATITUDE_AND_LONGITUDE_MINSK)
                .appendQueryParameter(PARAM_DATE_VERIFIED, VALUE_DATE_VERIFIED)
                .build()
                .toString();
    }

    public static String getVenuesByIdUrl(final String pId) {
        return VENUE_BASE_URI.buildUpon()
                .appendPath(pId)
                .appendQueryParameter(PARAM_DATE_VERIFIED, VALUE_DATE_VERIFIED)
                .build()
                .toString();
    }

    public static HttpRequest getVenuesNearbyRequest() {
        final String url = VENUE_BASE_URI.buildUpon()
                .appendPath(PATH_NEARBY)
                .build()
                .toString();

        final String body = String.format(PARAM_TEMPLATE, PARAM_DATE_VERIFIED, VALUE_DATE_VERIFIED)
                + PARAM_SEPARATOR
                + String.format(PARAM_TEMPLATE, PARAM_LATITUDE_AND_LONGITUDE, VALUE_LATITUDE_AND_LONGITUDE_MINSK);

        return new HttpRequest.Builder()
                .setUrl(url)
                .setRequestType(HttpRequestType.POST)
                .setBody(body)
                .build();
    }
}
