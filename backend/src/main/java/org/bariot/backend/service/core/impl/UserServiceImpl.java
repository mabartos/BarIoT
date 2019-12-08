package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.IsUnique;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends CRUDServiceSubItemsImpl<UserModel, HomeModel> implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    public UserServiceImpl(JpaRepository<UserModel, Long> repository, UpdateHelper<UserModel> updateHelper) {
        super(repository, updateHelper);
    }

    @Override
    public UserModel create(UserModel model) {
        try {
            if (model != null && model.getPassword() != null) {
                model.setPassword(encoder.encode(model.getPassword()));
                if (model.getEmail() != null && IsUnique.emailExists((UsersRepository) getRepository(), model.getEmail()))
                    return null;
                return getRepository().save(model);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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