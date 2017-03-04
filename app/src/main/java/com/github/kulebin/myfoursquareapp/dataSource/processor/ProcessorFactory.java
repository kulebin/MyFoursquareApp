package com.github.kulebin.myfoursquareapp.dataSource.processor;

final class ProcessorFactory {

    public static IProcessor create(final ProcessorType pType) throws Exception {
        return (IProcessor) pType.getType().newInstance();
    }
}
