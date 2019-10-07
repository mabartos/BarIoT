package org.bariot.backend.utils;

import java.util.List;

public interface IbasicInfo<U> {

    Long getId();

    String getName();

    boolean addToSubSet(U item);

    long getCountOfSub();

    List<U> getAllSubs();
}
