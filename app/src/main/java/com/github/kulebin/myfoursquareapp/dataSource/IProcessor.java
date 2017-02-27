package com.github.kulebin.myfoursquareapp.dataSource;

interface IProcessor {

    <Result> Result processData(String json);

}
