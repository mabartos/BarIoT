package org.bariot.backend.persistence.repo.users;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.RepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserModel, Long>, RepositoryCustom<UserModel> {
}