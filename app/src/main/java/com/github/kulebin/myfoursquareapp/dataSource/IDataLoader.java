package com.github.kulebin.myfoursquareapp.dataSource;

import com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType;
import com.github.kulebin.myfoursquareapp.http.HttpRequest;

interface IDataLoader {

    <Result> void loadData(String url, IOnResultCallback<Result> pOnResultCallback, ProcessorType pType);

    <Result> void loadData(HttpRequest pRequest, IOnResultCallback<Result> pOnResultCallback, ProcessorType pType);

    final class Impl {

        public static IDataLoader newInstance() {
            return new DataLoader();
        }
    }

}
