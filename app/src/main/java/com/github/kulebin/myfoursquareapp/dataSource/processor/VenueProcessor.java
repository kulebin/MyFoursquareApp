package com.github.kulebin.myfoursquareapp.dataSource.processor;

import com.github.kulebin.myfoursquareapp.model.Location;
import com.github.kulebin.myfoursquareapp.model.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class VenueProcessor extends BaseProcessor implements IProcessor {

    private static final String VENUE_OBJECT = "venue";
    private static final String VENUE_ID = "id";
    private static final String VENUE_NAME = "name";
    private static final String VENUE_RATING = "rating";
    private static final String VENUE_DESCRIPTION = "description";

    private static final String CONTACT_OBJECT = "contact";
    private static final String CONTACT_FORMATTED_PHONE = "formattedPhone";
    private static final String CONTACT_PHONE = "phone";

    private static final String LOCATION_OBJECT = "location";
    private static final String LOCATION_LATITUDE = "lat";
    private static final String LOCATION_LONGITUDE = "lng";
    private static final String LOCATION_ADDRESS = "address";
    private static final String LOCATION_DISTANCE = "distance";
    private static final String LOCATION_CITY = "city";
    private static final String LOCATION_COUNTRY = "country";

    private static final String PHOTOS_OBJECT = "photos";
    private static final String BEST_PHOTO_OBJECT = "bestPhoto";
    private static final String PHOTOS_COUNT = "count";
    private static final String PHOTOS_GROUPS_ARRAY = "groups";
    private static final String PHOTOS_GROUPS_ITEMS_ARRAY = "items";

    @Override
    public Object processData(final String json) {
        try {
            final JSONObject responseObject = getResponseObject(json);
            final JSONObject venueObject = responseObject.getJSONObject(VENUE_OBJECT);

            return parseVenueObject(venueObject);
        } catch (final JSONException pE) {
            return null;
        }
    }

    protected Venue parseVenueObject(final JSONObject pVenueObject) throws JSONException {
        return new Venue(
                pVenueObject.getString(VENUE_ID),
                pVenueObject.getString(VENUE_NAME),
                pVenueObject.has(CONTACT_OBJECT) ? getContact(pVenueObject.getJSONObject(CONTACT_OBJECT)) : null,
                getLocation(pVenueObject.getJSONObject(LOCATION_OBJECT)),
                pVenueObject.has(VENUE_RATING) ? Float.valueOf(pVenueObject.getString(VENUE_RATING)) : -1,
                getImageUrl(pVenueObject),
                pVenueObject.has(VENUE_DESCRIPTION) ? pVenueObject.getString(VENUE_DESCRIPTION) : null
        );
    }

    private String getContact(final JSONObject pContactObject) throws JSONException {
        if (pContactObject.has(CONTACT_FORMATTED_PHONE)) {
            return pContactObject.getString(CONTACT_FORMATTED_PHONE);
        } else if (pContactObject.has(CONTACT_PHONE)) {
            return pContactObject.getString(CONTACT_PHONE);
        } else {
            return null;
        }
    }

    private Location getLocation(final JSONObject pJSONObject) throws JSONException {
        return new Location(
                pJSONObject.has(LOCATION_LATITUDE) ? pJSONObject.getDouble(LOCATION_LATITUDE) : -1,
                pJSONObject.has(LOCATION_LONGITUDE) ? pJSONObject.getDouble(LOCATION_LONGITUDE) : -1,
                pJSONObject.has(LOCATION_ADDRESS) ? pJSONObject.getString(LOCATION_ADDRESS) : null,
                pJSONObject.has(LOCATION_DISTANCE) ? pJSONObject.getInt(LOCATION_DISTANCE) : -1,
                pJSONObject.has(LOCATION_CITY) ? pJSONObject.getString(LOCATION_CITY) : null,
                pJSONObject.has(LOCATION_COUNTRY) ? pJSONObject.getString(LOCATION_COUNTRY) : null
        );
    }

    private String getImageUrl(final JSONObject pJSONObject) throws JSONException {
        if (pJSONObject.has(BEST_PHOTO_OBJECT)) {
            return parsePhotoObject(pJSONObject.getJSONObject(BEST_PHOTO_OBJECT));
        } else if (pJSONObject.has(PHOTOS_OBJECT)) {
            if (pJSONObject.getInt(PHOTOS_COUNT) > 0) {
                final JSONArray groups = pJSONObject.getJSONArray(PHOTOS_GROUPS_ARRAY);
                final JSONArray items = groups.getJSONObject(0).getJSONArray(PHOTOS_GROUPS_ITEMS_ARRAY);

                for (int i = 0; i < items.length(); i++) {
                    final JSONObject item = items.getJSONObject(i);
                    final String url = parsePhotoObject(item);

                    if (url != null) {
                        return url;
                    }
                }
            }
        }
        return null;
    }
}
