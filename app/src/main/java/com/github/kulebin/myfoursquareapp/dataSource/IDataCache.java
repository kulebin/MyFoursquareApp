package com.github.kulebin.myfoursquareapp.dataSource;

interface IDataCache {

    boolean isDataCached (String url);

    <Data> void cacheData(Data data);

}
