package com.github.kulebin.myfoursquareapp.dataSource.processor;

import com.github.kulebin.myfoursquareapp.model.Venue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsProcessor extends BaseProcessor implements IProcessor {

    private static final String GROUP_OBJECT = "group";
    private static final String TOTAL_RESULTS = "totalResults";
    private static final String RESULTS_ARRAY = "results";
    private static final String PHOTO_OBJECT = "photo";
    private static final String DISPLAY_TYPE = "displayType";
    private static final String VENUE_OBJECT = "venue";

    @Override
    public List<Venue> processData(final String json) {
        try {
            final JSONObject responseObject = getResponseObject(json);
            final JSONObject groupObject = responseObject.getJSONObject(GROUP_OBJECT);
            if (groupObject.getInt(TOTAL_RESULTS) > 0) {
                final JSONArray resultsArray = groupObject.getJSONArray(RESULTS_ARRAY);
                final List<Venue> venueList = new ArrayList<>();
                final VenueProcessor venueProcessor = new VenueListProcessor();

                for (int i = 0; i < resultsArray.length(); i++) {
                    if (VENUE_OBJECT.equals(resultsArray.getJSONObject(i).getString(DISPLAY_TYPE))) {
                        final Venue venue = venueProcessor.parseVenueObject(resultsArray.getJSONObject(i).getJSONObject(VENUE_OBJECT));
                        if (resultsArray.getJSONObject(i).has(PHOTO_OBJECT)) {
                            venue.setImageUrl(parsePhotoObject(resultsArray.getJSONObject(i).getJSONObject(PHOTO_OBJECT)));
                        }
                        venueList.add(venue);
                    }
                }

                return venueList;
            } else {
                return null;
            }
        } catch (final Exception pE) {
            return null;
        }
    }
}
