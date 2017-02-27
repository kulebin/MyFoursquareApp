package com.github.kulebin.myfoursquareapp.dataSource;

interface IDataLoader {

    <Result> void loadData(String url, IOnResultCallback<Result> pOnResultCallback);

    final class Impl {

        public static IDataLoader newInstance() {
            return new DataLoader();
        }
    }

}
