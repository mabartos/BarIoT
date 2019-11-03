package org.bariot.backend.persistence.repo;

import org.bariot.backend.persistence.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserModel, Long> {
    @Modifying
    @Query(value = "DELETE FROM USERS WHERE USER_ID=?1",nativeQuery = true)
    void deleteHomeById(long id);
}