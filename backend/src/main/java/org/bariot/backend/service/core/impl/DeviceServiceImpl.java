package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.service.core.DeviceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl extends CRUDServiceImpl<DeviceModel> implements DeviceService {
    public DeviceServiceImpl(JpaRepository<DeviceModel, Long> repository) {
        super(repository);
    }
}
