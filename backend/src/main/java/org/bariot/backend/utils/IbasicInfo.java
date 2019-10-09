package org.bariot.backend.utils;

import java.util.List;

public interface IbasicInfo<U> {

    Long getId();
    void setId(Long id);

    String getName();

    boolean addToSubSet(U item);

    long getCountOfSub();

    List<U> getAllSubs();
}
