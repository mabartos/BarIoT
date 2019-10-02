package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/homes")
@Transactional
public class HomesResource {

    @Autowired
    private HomesRepository homesRepository;

    private ResponseHelper<HomeModel, HomesRepository> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelper<>(homesRepository);
    }

    @GetMapping()
    public List<HomeModel> getAllHomes() {
        return homesRepository.findAll();
    }

    @GetMapping("/{idOrName}")
    public ResponseEntity<HomeModel> getHomeByIdOrName(@PathVariable("idOrName") String idOrName) {
        return helper.getByIdOrName(idOrName);
    }

    @PostMapping()
    public ResponseEntity<HomeModel> createHome(@RequestBody HomeModel home) {
        return helper.create(home);
    }

    @GetMapping("/delete/{idOrName}")
    public ResponseEntity<HomeModel> deleteHome(@PathVariable("idOrName") String idOrName) {
        return helper.deleteByIdOrName(idOrName);
    }
}
