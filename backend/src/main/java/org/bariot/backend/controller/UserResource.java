package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/users")
@Transactional
public class UserResource {

    public static final String USER_MAPPING = "/users";

    @Autowired
    private UsersRepository usersRepository;

    private ResponseHelper<UserModel, UsersRepository> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelper<>(usersRepository);
    }

    @GetMapping()
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return helper.getAll();
    }

    @PostMapping("/{username}")
    public ResponseEntity<UserModel> createUserByUsername(@PathVariable("username") String username) {
        UserModel user = new UserModel();
        if (usersRepository.save(user) != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.badRequest().build();
    }

    @PostMapping()
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        return helper.create(user);
    }

    @DeleteMapping("/{idOrName}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable("idOrName") String idOrName) {
        return helper.deleteByIdOrName(idOrName);
    }

    @GetMapping("/{idOrName}")
    public ResponseEntity<UserModel> getUserByIdOrName(@PathVariable("idOrName") String idOrName) {
        return helper.getByIdOrName(idOrName);
    }
}
