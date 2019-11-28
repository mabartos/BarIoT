package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.CRUDService;
import org.bariot.backend.service.core.CRUDServiceSubItems;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.responseHelper.ResponseHelperWithSub;
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

import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/homes")
@SuppressWarnings("unchecked")
public class HomeResource {
    private static final String HOME_ID = "/{idHome:[\\d]+}";

    private ResponseHelperWithSub<HomeModel, UserModel> helper;

    public HomeResource(HomeService homeService, UserService userService) {
        this.helper = new ResponseHelperWithSub(homeService, userService);
    }

    @PostMapping()
    public ResponseEntity createHome(@RequestBody HomeModel home) {
        return helper.create(home);
    }

    @PutMapping(HOME_ID)
    public ResponseEntity updateUser(@PathVariable("idHome") Long id, @RequestBody HomeModel home) {
        return helper.update(id, home);
    }

    @PatchMapping(HOME_ID)
    public ResponseEntity updateHomeItems(@PathVariable("idHome") Long id, @RequestBody String updatesJSON) {
        return helper.updateByProps(id, updatesJSON);
    }

    @GetMapping(HOME_ID)
    public ResponseEntity findById(@PathVariable("idHome") Long id) {
        return helper.getByID(id);
    }

    @GetMapping()
    public ResponseEntity getHomes() {
        return helper.getAll();
    }

    @DeleteMapping(HOME_ID)
    public ResponseEntity deleteHome(@PathVariable("idHome") Long id) {
        return helper.deleteByID(id);
    }
}
