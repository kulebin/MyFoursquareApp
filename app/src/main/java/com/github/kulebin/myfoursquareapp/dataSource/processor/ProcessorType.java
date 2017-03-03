package com.github.kulebin.myfoursquareapp.dataSource.processor;

public enum ProcessorType {

    VENUE(VenueProcessor.class);

    private final Class clazz;

    ProcessorType(final Class clazz) {
        this.clazz = clazz;
    }

    public Class getValue() {
        return clazz;
    }
}
