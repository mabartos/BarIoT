package org.bariot.backend.utils;

import java.util.List;

public interface IHasSubitems<Child> {
    boolean addToSubSet(Child item);

    Integer getCountOfSub();

    List<Child> getAllSubs();
}
