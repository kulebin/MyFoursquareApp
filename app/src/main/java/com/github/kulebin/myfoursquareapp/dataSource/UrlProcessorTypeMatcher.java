package com.github.kulebin.myfoursquareapp.dataSource;

import android.content.UriMatcher;
import android.net.Uri;

import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType;

import static com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType.VENUE;
import static com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType.VENUES_LIST;

final class UrlProcessorTypeMatcher {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Api.AUTHORITY, Api.API_VERSION + "/" + Api.PATH_VENUES + "/" + Api.PATH_TRENDING, VENUES_LIST.getCode());
        matcher.addURI(Api.AUTHORITY, Api.API_VERSION + "/" + Api.PATH_VENUES + "/*", VENUE.getCode());

        return matcher;
    }

    public static ProcessorType getProcessorType(final String pUrl) throws Exception {
        final Uri uri = Uri.parse(pUrl).buildUpon().build();
        final int matchCode = sUriMatcher.match(uri);

        if (matchCode == VENUES_LIST.getCode()) {
            return VENUES_LIST;
        } else if (matchCode == VENUE.getCode()) {
            return VENUE;
        } else {
            throw new Exception("Processor type is unknown");
        }
    }
}
