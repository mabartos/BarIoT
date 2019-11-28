package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.service.core.UserHomeService;
import org.bariot.backend.utils.responseHelper.UserHomeResponseHelper;
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

import static org.bariot.backend.controller.UserResource.USER_MAPPING;

@RequestMapping(USER_MAPPING)
@RestController
@Transactional
public class UserHomeResource {
    private static final String HOME_BASIC_URL = "/{id:[\\d]+}/homes";
    private static final String HOME_ID = "/{idHome:[\\d]+}";

    public static final String HOME_MAPPING = UserResource.USER_MAPPING + HOME_BASIC_URL + HOME_ID;

    private UserHomeResponseHelper userHomeHelper;

    public UserHomeResource(UserHomeService userHomeService) {
        this.userHomeHelper = new UserHomeResponseHelper(userHomeService);
    }

    @GetMapping(HOME_BASIC_URL)
    public ResponseEntity getUsersHomes(@PathVariable("id") Long id) {
        return userHomeHelper.getUsersHomes(id);
    }

    @PostMapping(HOME_BASIC_URL)
    public ResponseEntity createHomeForUser(@PathVariable("id") Long id, @RequestBody HomeModel home) {
        return userHomeHelper.createHomeForUser(id, home);
    }

    @PostMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity addExistingHomeToUser(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return userHomeHelper.addExistingHome(id, idHome);
    }

    @DeleteMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity removeHomeFromUser(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return userHomeHelper.removeHomeFromUser(id, idHome);
    }

    @GetMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity getHomeFromUserByID(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return userHomeHelper.getHomeByID(id, idHome);
    }

    @PutMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity updateHomeInUser(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @RequestBody HomeModel home
    ) {
        return userHomeHelper.updateHome(id, idHome, home);
    }

    @PatchMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity updateHomeInUserItems(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @RequestBody String updatesJSON
    ) {
        return userHomeHelper.updateHomeItems(id, idHome, updatesJSON);
    }
}
