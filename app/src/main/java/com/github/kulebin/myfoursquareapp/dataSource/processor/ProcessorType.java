package com.github.kulebin.myfoursquareapp.dataSource.processor;

public enum ProcessorType {

    VENUE(VenueProcessor.class, 100),
    VENUES_LIST(VenueListProcessor.class, 101);

    private final Class<? extends IProcessor> clazz;
    private final int typeCode;

    ProcessorType(final Class<? extends IProcessor> clazz, final int typeCode) {
        this.clazz = clazz;
        this.typeCode = typeCode;
    }

    public Class getType() {
        return clazz;
    }

    public int getCode() {
        return typeCode;
    }
}
