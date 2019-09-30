package org.bariot.backend.persistence.repo.users;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.RepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

public class RepositoryCustomImpl implements RepositoryCustom<UserModel> {

    @Autowired
    @Lazy
    UsersRepository repository;

    @Override
    public UserModel getByName(String name) {
        Optional opts = repository.findAll().stream().filter(f -> f.getUsername().equals(name)).findAny();
        if (opts.isPresent())
            return (UserModel) opts.get();
        else
            return null;
    }
}
