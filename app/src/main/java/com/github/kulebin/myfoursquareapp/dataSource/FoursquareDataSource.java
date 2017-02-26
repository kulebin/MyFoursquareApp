package com.github.kulebin.myfoursquareapp.dataSource;

import android.os.Handler;

import com.github.kulebin.myfoursquareapp.api.Api;
import com.github.kulebin.myfoursquareapp.http.IHttpClient;
import com.github.kulebin.myfoursquareapp.model.Venue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FoursquareDataSource implements IDataSource {

    @Override
    public <Result> void fetchData(final String pUrl, final IOnResultCallback<Result> pOnResultCallback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                pOnResultCallback.onStart();
                IHttpClient.Impl.get().doRequest(pUrl, new IHttpClient.IOnResult() {

                    @Override
                    public void onSuccess(final String result) {
                        final Result data = parseResponse(pUrl, result);

                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pOnResultCallback.onSuccess(data);
                            }
                        });
                    }

                    @Override
                    public void onError(final IOException e) {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pOnResultCallback.onError(e);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private <T> T parseResponse(String pUrl, String pResponse) {
        //todo parsing logic should be implemented here
        if (Api.getVenuesTrendingUrl().equals(pUrl)) {
            return (T) initVenueList();
        } else {
            return (T) new Venue("4347394793479", pResponse, "some location", "contacts", 5.6F, null);
        }
    }

    private List<Venue> initVenueList() {
        final List<Venue> venueList = new ArrayList<>();
        venueList.add(new Venue("4347394793479", "place 1", "some location", "contacts", 5.6F, null));
        venueList.add(new Venue("43erojodfjjd", "place 2", "some location2", "contacts2", 6.6F, null));
        venueList.add(new Venue("8549jj59494", "place 3", "some location3", "contacts3", 4.6F, null));
        venueList.add(new Venue("564884349389", "place 4", "some location4", "contacts4", 3.6F, null));
        venueList.add(new Venue("9948598jieik", "place 5", "some location5", "contacts5", 7.3F, null));
        venueList.add(new Venue("63793749393", "place 6", "some location6", "contacts6", 8.3F, null));
        venueList.add(new Venue("9458947594", "place 7", "some location7", "contacts7", 9.3F, null));
        return venueList;
    }
}
