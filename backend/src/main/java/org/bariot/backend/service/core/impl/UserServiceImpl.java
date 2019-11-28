package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends CRUDServiceSubItemsImpl<UserModel, HomeModel> implements UserService {
    public UserServiceImpl(JpaRepository<UserModel, Long> repository, UpdateHelper<UserModel> updateHelper) {
        super(repository, updateHelper);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        List<UserModel> users = getRepository().findAll();
        if (users != null && !users.isEmpty() && username != null) {
            Optional opt = users.stream().filter(f -> f.getUsername().equals(username)).findAny();
            if (opt.isPresent())
                return (UserModel) opt.get();
        }
        return null;
    }
}