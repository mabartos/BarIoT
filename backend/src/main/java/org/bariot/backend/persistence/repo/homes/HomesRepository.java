package org.bariot.backend.persistence.repo.homes;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.repo.RepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomesRepository extends JpaRepository<HomeModel, Long>, RepositoryCustom<HomeModel> {
}
