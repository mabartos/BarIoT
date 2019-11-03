package org.bariot.backend.persistence.repo;

import org.bariot.backend.persistence.model.HomeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HomesRepository extends JpaRepository<HomeModel, Long> {
    @Modifying
    @Query(value = "DELETE FROM HOMES WHERE HOME_ID=?1",nativeQuery = true)
    void deleteHomeById(long id);
}
