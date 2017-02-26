package com.github.kulebin.myfoursquareapp.dataSource;

public interface IDataSource {

    //void fetchVenueList(IOnResultCallback pOnResultCallback);

    <Result> void fetchData(String pUrl, IOnResultCallback<Result> pOnResultCallback);

    final class Impl {

        public static IDataSource newInstance() {
            return new FoursquareDataSource();
        }
    }

}
