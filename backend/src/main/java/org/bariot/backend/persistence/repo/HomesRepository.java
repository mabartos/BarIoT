package org.bariot.backend.persistence.repo;

import org.bariot.backend.persistence.model.HomeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomesRepository extends JpaRepository<HomeModel, Long> {
}
