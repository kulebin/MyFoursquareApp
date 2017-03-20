package com.github.kulebin.myfoursquareapp.dataSource.processor;

public enum ProcessorType {

    VENUE(VenueProcessor.class),
    VENUES_LIST(VenueListProcessor.class),
    RECOMMENDATIONS(RecommendationsProcessor.class);

    private final Class<? extends IProcessor> clazz;

    ProcessorType(final Class<? extends IProcessor> clazz) {
        this.clazz = clazz;
    }

    public Class getType() {
        return clazz;
    }
}
