package com.github.kulebin.myfoursquareapp.dataSource;

import com.github.kulebin.myfoursquareapp.dataSource.processor.ProcessorType;

interface IDataLoader {

    <Result> void loadData(String url, IOnResultCallback<Result> pOnResultCallback, ProcessorType pType);

    final class Impl {

        public static IDataLoader newInstance() {
            return new DataLoader();
        }
    }

}
