package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/homes")
public class HomeResource {

    @Autowired
    HomesRepository homesRepo;

    private ResponseHelper<HomeModel, HomesRepository> helper;
    private static final String HOME_ID = "/{idHome:[\\d]+}";

    @PostConstruct
    public void init() {
        helper = new ResponseHelper<>(homesRepo);
    }

    @PostMapping()
    public ResponseEntity<HomeModel> createHome(@RequestBody HomeModel home) {
        return helper.create(home);
    }

    @PutMapping(HOME_ID)
    public ResponseEntity<HomeModel> updateUser(@PathVariable("idHome") Long id, @RequestBody HomeModel home) {
        return helper.update(id, home);
    }

    @PatchMapping(HOME_ID)
    public ResponseEntity<HomeModel> updateHomeItems(@PathVariable("idHome") Long id, @RequestBody Map<String, String> updates) {
        return helper.update(id, updates);
    }

    @GetMapping(HOME_ID)
    public ResponseEntity<HomeModel> findById(@PathVariable("idHome") Long id) {
        return helper.getById(id);
    }

    @GetMapping()
    public ResponseEntity<List<HomeModel>> getHomes() {
        return helper.getAll();
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<HomeModel> deleteHome(@PathVariable("id") Long id) {
        return helper.deleteById(id);
    }
}
