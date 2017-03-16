package com.github.kulebin.myfoursquareapp.dataSource.processor;

import org.json.JSONException;
import org.json.JSONObject;

class BaseProcessor {

    private static final String RESPONSE_OBJECT = "response";

    private static final String PHOTO_URL_TEMPLATE = "%s%sx%s%s"; /*Url scheme: "prefix" + "width" + "x" + "height" + "suffix" */
    private static final String PHOTO_PREFIX = "prefix";
    private static final String PHOTO_SUFFIX = "suffix";
    private static final String PHOTO_WIDTH = "width";
    private static final String PHOTO_HEIGHT = "height";
    private static final String PHOTO_VISIBILITY = "visibility";
    private static final String PHOTO_VISIBILITY_VALUE_PUBLIC = "public";

    protected JSONObject getResponseObject(final String pJson) throws JSONException {
        final JSONObject jsonObject = new JSONObject(pJson);

        return jsonObject.getJSONObject(RESPONSE_OBJECT);
    }

    protected String parsePhotoObject(final JSONObject pPhotoObject) throws JSONException {
        if (PHOTO_VISIBILITY_VALUE_PUBLIC.equals(pPhotoObject.getString(PHOTO_VISIBILITY))) {
            return String.format(PHOTO_URL_TEMPLATE,
                    pPhotoObject.getString(PHOTO_PREFIX),
                    pPhotoObject.getString(PHOTO_WIDTH),
                    pPhotoObject.getString(PHOTO_HEIGHT),
                    pPhotoObject.getString(PHOTO_SUFFIX));
        } else {
            return null;
        }
    }
}
