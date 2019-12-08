package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.service.core.RoomService;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl extends CRUDServiceSubItemsImpl<RoomModel, DeviceModel> implements RoomService {
    public RoomServiceImpl(JpaRepository<RoomModel, Long> repository, UpdateHelper<RoomModel> updateHelper) {
        super(repository, updateHelper);
    }

    @Override
    public boolean deleteByID(long id) {
        try {
            RoomModel entity = getByID(id);
            if (entity == null || !entity.removeThisFromHome())
                return false;
            entity.removeDevicesFromRoom();
            getRepository().deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}