package com.github.kulebin.myfoursquareapp.dataSource.processor;

import android.support.annotation.Nullable;

final class ProcessorFactory {

    @Nullable
    public static IProcessor get(final ProcessorType pType) {
        try {
            return (IProcessor) pType.getValue().newInstance();
        } catch (final InstantiationException pE) {
            return null;
        } catch (final IllegalAccessException pE) {
            return null;
        }
    }
}
