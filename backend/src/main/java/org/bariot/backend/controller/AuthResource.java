package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.auth.SecurityUserService;
import org.bariot.backend.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;


@RestController
@RequestMapping("/auth")
@Transactional
public class AuthResource {

    private SecurityUserService securityUserService;
    private UserService userService;

    public AuthResource(SecurityUserService securityUserService, UserService userService) {
        this.securityUserService = securityUserService;
        this.userService = userService;
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@QueryParam("username") String username, @QueryParam("password") String password) {
        if (username == null || password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");
        securityUserService.autoLogin(username, password);
        UserModel user = userService.getUserByUsername(username);
        String authJsonObj = securityUserService.getAuthJson(username, password, user);
        if (authJsonObj == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create Auth JSON object");
        }
        return ResponseEntity.ok(authJsonObj);
    }

    @GetMapping("/register")
    public ResponseEntity register(
            @QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("firstname") String firstname,
            @QueryParam("lastname") String lastname
    ) {
        if (username == null || password == null || firstname == null || lastname == null)
            return ResponseEntity.badRequest().body("Missing account details!");
        UserModel user = new UserModel(username, password, firstname, lastname);
        if (userService.create(user) == null) {
            return ResponseEntity.badRequest().body("Cannot create user. Invalid data.");
        }
        securityUserService.autoLogin(username, password);
        String authJsonObj = securityUserService.getAuthJson(username, password, user);
        if (authJsonObj == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot create Auth JSON object");
        }
        return ResponseEntity.ok(authJsonObj);
    }

    @GetMapping("/token")
    public ResponseEntity getToken(@QueryParam("email") String email, @QueryParam("password") String password) {
        if (email == null || password == null)
            return ResponseEntity.badRequest().body("Missing credentials!");
        return ResponseEntity.ok(securityUserService.getBasicToken(email, password));
    }

    @GetMapping("/logged")
    public ResponseEntity getLoggedUser(Authentication authentication) {
        if (authentication != null) {
            UserModel user = userService.getUserByUsername(authentication.getName());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find logged user");
            }
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find logged user");
    }

}
