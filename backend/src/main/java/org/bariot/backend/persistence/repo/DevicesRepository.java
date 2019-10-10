package org.bariot.backend.persistence.repo;

import org.bariot.backend.persistence.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevicesRepository extends JpaRepository<DeviceModel, Long> {
}
