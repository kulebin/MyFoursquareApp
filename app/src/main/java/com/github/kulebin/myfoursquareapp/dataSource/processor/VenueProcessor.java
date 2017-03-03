package com.github.kulebin.myfoursquareapp.dataSource.processor;

import com.github.kulebin.myfoursquareapp.model.Venue;

import org.json.JSONException;
import org.json.JSONObject;

class VenueProcessor implements IProcessor {

    @Override
    public Venue processData(final String json) {
        try {
            final JSONObject jsonObject = new JSONObject(json);
            return new Venue(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("contact"),
                    jsonObject.getString("location"),
                    Float.valueOf(jsonObject.getString("rating")),
                    jsonObject.getString("photos")
            );
        } catch (final JSONException pE) {
            return null;
        }
    }
}
