package com.github.kulebin.myfoursquareapp.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LifoBlockingDeque<E> extends LinkedBlockingDeque<E> {

    @Override
    public boolean offer(final E e) {
        return super.offerFirst(e);
    }

    @Override
    public boolean offer(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return super.offerFirst(e, timeout, unit);
    }

    @Override
    public boolean add(final E e) {
        return super.offerFirst(e);
    }

    @Override
    public void put(final E e) throws InterruptedException {
        super.putFirst(e);
    }
}
