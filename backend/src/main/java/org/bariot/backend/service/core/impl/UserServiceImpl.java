package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CRUDServiceSubItemsImpl<UserModel, HomeModel> implements UserService {
    public UserServiceImpl(JpaRepository<UserModel, Long> repository) {
        super(repository);
    }
}