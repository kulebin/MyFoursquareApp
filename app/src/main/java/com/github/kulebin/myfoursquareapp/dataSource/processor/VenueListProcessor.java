package com.github.kulebin.myfoursquareapp.dataSource.processor;

import com.github.kulebin.myfoursquareapp.model.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class VenueListProcessor extends VenueProcessor {

    private static final String VENUES_ARRAY = "venues";

    @Override
    public Object processData(final String json) {
        try {
            final JSONObject responseObject = getResponseObject(json);
            final JSONArray venuesArray = responseObject.getJSONArray(VENUES_ARRAY);
            final List<Venue> venueList = new ArrayList<>(venuesArray.length());

            for (int i = 0; i < venuesArray.length(); i++) {
                venueList.add(parseVenueObject(venuesArray.getJSONObject(i)));
            }

            return venueList;
        } catch (final JSONException pE) {
            return null;
        }
    }

}
