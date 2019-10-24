package org.bariot.backend.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.utils.ResponseHelperMulti;
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

import static org.bariot.backend.controller.UserResource.USER_MAPPING;

@RequestMapping(USER_MAPPING)
@RestController
@Transactional
public class UserHomeResource {
    private static final String HOME_BASIC_URL = "/{id:[\\d]+}/homes";
    private static final String HOME_ID = "/{idHome:[\\d]+}";

    @JsonIgnore
    public static final String HOME_MAPPING = UserResource.USER_MAPPING + HOME_BASIC_URL + HOME_ID;

    @Autowired
    private HomesRepository homesRepo;

    @Autowired
    private UsersRepository userRepo;

    private ResponseHelperMulti<UserModel, HomeModel> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelperMulti<>(userRepo, homesRepo);
    }

    // Basic operations

    @GetMapping(HOME_BASIC_URL)
    public ResponseEntity<List<HomeModel>> getUsersHomes(@PathVariable("id") Long id) {
        if (helper.isInited(id)) {
            return helper.getAll();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(HOME_BASIC_URL)
    public ResponseEntity<HomeModel> createHome(@PathVariable("id") Long id, @RequestBody HomeModel home) {
        if (helper.isInited(id)) {
            return helper.createItem(home);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> addExistingHome(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        if (helper.isInited(id)) {
            return helper.addExistingHomeToUser(idHome);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> removeHomeFromUser(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        if (helper.isInited(id)) {
            return helper.deleteItemById(idHome);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(HOME_BASIC_URL)
    public ResponseEntity<Void> removeAllHomesFromUser(@PathVariable("id") Long id) {
        if (helper.isInited(id)) {
            return helper.removeAllSub();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> getHomeByID(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        if (helper.isInited(id)) {
            return helper.getItem(idHome);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> updateHome(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @RequestBody HomeModel home
    ) {
        if (helper.isInited(id)) {
            return helper.updateItem(idHome, home);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> updateHomeItems(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @RequestBody Map<String, String> updates
    ) {
        if (helper.isInited(id)) {
            return helper.updateItemProps(idHome, updates);
        }
        return ResponseEntity.notFound().build();
    }
}
