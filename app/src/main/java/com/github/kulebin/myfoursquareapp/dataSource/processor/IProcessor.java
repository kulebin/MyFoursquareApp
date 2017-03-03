package com.github.kulebin.myfoursquareapp.dataSource.processor;

public interface IProcessor {

    <Result> Result processData(String json);

    final class Impl {

        public static IProcessor get(final ProcessorType pType) {
            return ProcessorFactory.get(pType);
        }

    }

}
