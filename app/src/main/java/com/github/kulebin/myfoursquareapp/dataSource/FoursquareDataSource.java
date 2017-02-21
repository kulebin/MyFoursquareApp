package com.github.kulebin.myfoursquareapp.dataSource;

import com.github.kulebin.myfoursquareapp.model.Venue;
import com.github.kulebin.myfoursquareapp.thread.ITask;
import com.github.kulebin.myfoursquareapp.thread.IThreadManager;
import com.github.kulebin.myfoursquareapp.thread.OnResultCallback;
import com.github.kulebin.myfoursquareapp.thread.ProgressCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FoursquareDataSource implements EntityGateway {

    @Override
    public void fetchVenueList(final OnResultCallback pOnResultCallback) {
        IThreadManager.Impl.get().execute(
                new ITask<Void, Void, List<Venue>>() {

                    @Override
                    public List<Venue> perform(Void pVoid, ProgressCallback<Void> progressCallback) throws Exception {
                        //Long operation imitation
                        TimeUnit.SECONDS.sleep(2);
                        return initVenueList();
                    }
                },
                null,
                pOnResultCallback);
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
