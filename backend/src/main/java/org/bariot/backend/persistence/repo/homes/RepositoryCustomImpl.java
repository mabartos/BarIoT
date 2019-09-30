package org.bariot.backend.persistence.repo.homes;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.repo.RepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

//TODO
public class RepositoryCustomImpl implements RepositoryCustom<HomeModel> {

    @Autowired
    @Lazy
    HomesRepository repository;

    @Override
    public HomeModel getByName(String name) {
        Optional opts = repository.findAll().stream().filter(f -> f.getName().equals(name)).findAny();
        if (opts.isPresent())
            return (HomeModel) opts.get();
        else
            return null;
    }
}
