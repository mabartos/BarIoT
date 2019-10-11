package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.utils.ResponseHelper;
import org.bariot.backend.utils.ResponseHelperMulti;
import org.bariot.backend.utils.UpdateHelper;
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
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Transactional
public class UserResource {

    public static final String USER_MAPPING = "/users";
    private static final String USER_ID = "/{id:[\\d]+}";

    @Autowired
    private UsersRepository usersRepository;

    private ResponseHelper<UserModel, UsersRepository> helper;

    private ResponseHelperMulti<UserModel> mainHelper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelper<>(usersRepository);
        mainHelper = new ResponseHelperMulti<>();
    }

    @GetMapping()
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return helper.getAll();
    }

    @GetMapping(USER_ID)
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") long id) {
        return helper.getById(id);
    }

    @PostMapping("/{username}")
    public ResponseEntity<UserModel> createUserByUsername(@PathVariable("username") String username) {
        UserModel user = new UserModel(username);
        if (usersRepository.save(user) != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.badRequest().build();
    }

    @PostMapping()
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        return helper.create(user);
    }

    @PutMapping(USER_ID)
    public ResponseEntity<UserModel> updateUser(@PathVariable("id") Long id, @RequestBody UserModel user) {
        return helper.update(id, user);
    }

    @PatchMapping(USER_ID)
    public ResponseEntity<UserModel> updateUserItems(@PathVariable("id") Long id, @RequestBody Map<String, String> updates) {
        Optional userOpt = usersRepository.findById(id);
        if (userOpt.isPresent()) {
            UserModel user = (UserModel) userOpt.get();
            user = UpdateHelper.updateItems(user, updates);
            if (user != null) {
                usersRepository.save(user);
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(USER_ID)
    public ResponseEntity<UserModel> deleteUser(@PathVariable("id") Long idOrName) {
        return helper.deleteById(idOrName);
    }


}
