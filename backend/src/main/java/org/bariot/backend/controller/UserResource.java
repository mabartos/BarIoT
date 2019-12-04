package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
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

@RestController
@RequestMapping("/users")
@Transactional
@SuppressWarnings("unchecked")
public class UserResource {

    public static final String USER_MAPPING = "/users";
    private static final String USER_ID = "/{id:[\\d]+}";

    private ResponseHelperWithSub<UserModel, HomeModel> helper;

    public UserResource(UserService userService, HomeService homeService) {
        this.helper = new ResponseHelperWithSub(userService, homeService);
    }

    @GetMapping()
    public ResponseEntity getAllUsers() {
        return helper.getAll();
    }

    @GetMapping(USER_ID)
    public ResponseEntity getUserById(@PathVariable("id") long id) {
        return helper.getByID(id);
    }

    @PostMapping()
    public ResponseEntity createUser(@RequestBody UserModel user) {
        System.out.println(user.getPassword());
        return helper.create(user);
    }

    @PutMapping(USER_ID)
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserModel user) {
        return helper.update(id, user);
    }

    @PatchMapping(USER_ID)
    public ResponseEntity updateUserItems(@PathVariable("id") Long id, @RequestBody String updatesJson) {
        return helper.updateByProps(id, updatesJson);
    }

    @DeleteMapping(USER_ID)
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        return helper.deleteByID(id);
    }
}
