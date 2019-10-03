package org.bariot.backend.utils;

import java.util.List;

public interface IbasicInfo<U> {

    long getId();

    String getName();

    boolean addToSubSet(U item);

    long getCountOfSub();

    List<U> getAllSubs();
}
