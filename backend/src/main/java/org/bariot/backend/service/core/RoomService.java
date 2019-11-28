package org.bariot.backend.service.core;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.utils.IHasSubitems;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface RoomService extends CRUDServiceSubItems<RoomModel, DeviceModel> {
}
