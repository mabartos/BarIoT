package org.bariot.backend.controller;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.utils.responseHelper.HomeResponseHelper;
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
public class HomeResource {
    private static final String HOME_ID = "/{idHome:[\\d]+}";
    private static final String HOME_ID_ROLES = "/{idHome:[\\d]+}/roles";

    private HomeResponseHelper helper;

    public HomeResource(HomeService homeService) {
        this.helper = new HomeResponseHelper(homeService);
    }

    @PostMapping()
    public ResponseEntity createHome(@RequestBody HomeModel home) {
        return helper.create(home);
    }

    @PutMapping(HOME_ID)
    public ResponseEntity updateHome(@PathVariable("idHome") Long id, @RequestBody HomeModel home) {
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

    @GetMapping(HOME_ID_ROLES)
    public ResponseEntity getAllUserRolesInHome(@PathVariable("idHome") Long id) {
        return helper.getRolesFromHome(id);
    }

    @PatchMapping(HOME_ID_ROLES)
    public ResponseEntity updateUserRoleInHome(@PathVariable("idHome") Long id, @RequestBody DedicatedUserRole dedicatedUserRole) {
        return helper.updateUserRoleInHome(id, dedicatedUserRole);
    }

    @PostMapping(HOME_ID_ROLES)
    public ResponseEntity setUserRoleInHome(@PathVariable("idHome") Long id, @RequestBody DedicatedUserRole dedicatedUserRole) {
        return helper.setUserRoleInHome(id, dedicatedUserRole);
    }

    @DeleteMapping(HOME_ID_ROLES)
    public ResponseEntity removeUserRoleInHome(@PathVariable("idHome") Long id, @RequestBody DedicatedUserRole dedicatedUserRole) {
        return helper.removeUserRoleInHome(id, dedicatedUserRole);
    }

}
