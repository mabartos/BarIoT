package org.bariot.backend.persistence.model;

import org.bariot.backend.utils.IbasicInfo;

import java.util.List;

public class DeviceModel implements IbasicInfo {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean addToSubSet(Object item) {
        return false;
    }

    @Override
    public long getCountOfSub() {
        return 0;
    }

    @Override
    public List getAllSubs() {
        return null;
    }
}
