package com.github.kulebin.myfoursquareapp.api;

import android.net.Uri;

import com.github.kulebin.myfoursquareapp.BuildConfig;

public final class Api {

    private static final String BASE_URL = "https://api.foursquare.com/v2";

    private static final String PATH_VENUES = "venues";
    private static final String PATH_TRENDING = "trending";

    private static final String PARAM_DATE_VERIFIED = "v";
    private static final String PARAM_LATITUDE_AND_LONGITUDE = "ll";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_RADIUS = "radius";
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_CLIENT_SECRET = "client_secret";

    //temporarily hardcoded values
    private static final String VALUE_DATE_VERIFIED = "20170226"; /*Date value format: YYYYMMDD */
    private static final String VALUE_LATITUDE_AND_LONGITUDE_MINSK = "53.9,27.56667";

    private static final Uri VENUE_BASE_URI = Uri.parse(Api.BASE_URL)
            .buildUpon()
            .appendPath(PATH_VENUES)
            .appendQueryParameter(PARAM_DATE_VERIFIED, VALUE_DATE_VERIFIED)
            .appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.MY_4SQUARE_APP_CLIENT_ID)
            .appendQueryParameter(PARAM_CLIENT_SECRET, BuildConfig.MY_4SQUARE_APP_CLIENT_SECRET)
            .build();

    public static String getVenuesTrendingUrl() {
        return VENUE_BASE_URI.buildUpon()
                .appendPath(PATH_TRENDING)
                .appendQueryParameter(PARAM_LATITUDE_AND_LONGITUDE, VALUE_LATITUDE_AND_LONGITUDE_MINSK)
                .build()
                .toString();
    }

    public static String getVenuesByIdUrl(final String pId) {
        return VENUE_BASE_URI.buildUpon()
                .appendPath(pId)
                .build()
                .toString();
    }
}
