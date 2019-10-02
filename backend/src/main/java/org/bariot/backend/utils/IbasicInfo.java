package org.bariot.backend.utils;

public interface IbasicInfo<U> {

    long getId();

    String getName();

    boolean addToSubSet(U item);
}
