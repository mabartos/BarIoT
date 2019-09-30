package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.users.UsersRepository;
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
@RequestMapping("/users")
@Transactional
public class UsersResource {
    public static final String MAPPING = "/users";

    @Autowired
    private UsersRepository usersRepository;

    private ResponseHelper<UserModel, UsersRepository> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelper<>(usersRepository);
    }

    @GetMapping()
    public List<UserModel> getAllStudents() {
        return usersRepository.findAll();
    }

    @GetMapping("/{idOrName}")
    public ResponseEntity<UserModel> getUserByIdOrName(@PathVariable("idOrName") String idOrName) {
        return helper.getByIdOrName(idOrName);
    }

    @PostMapping()
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        return helper.create(user);
    }

    @GetMapping("/delete/{idOrName}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable("idOrName") String idOrName) {
        return helper.deleteByIdOrName(idOrName);
    }
}
