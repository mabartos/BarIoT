package org.bariot.backend.persistence.repo;

import org.bariot.backend.persistence.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends JpaRepository<RoomModel, Long> {
}
