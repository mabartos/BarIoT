package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/homes")
public class HomeResource {

    @Autowired
    HomesRepository homesRepo;

    private ResponseHelper<HomeModel, HomesRepository> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelper<>(homesRepo);
    }

    @GetMapping()
    public ResponseEntity<List<HomeModel>> getHomes() {
        return helper.getAll();
    }

    @DeleteMapping("/{id:[\\d]+]}")
    public ResponseEntity<HomeModel> deleteHome(@PathVariable("id") Long id) {
        return helper.deleteById(id);
    }
}
