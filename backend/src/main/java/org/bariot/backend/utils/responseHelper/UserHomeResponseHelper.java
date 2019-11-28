package org.bariot.backend.utils.responseHelper;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.service.core.UserHomeService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserHomeResponseHelper {

    private UserHomeService userHomeService;

    public UserHomeResponseHelper(UserHomeService userHomeService) {
        this.userHomeService = userHomeService;
    }

    public ResponseEntity getUsersHomes(Long id) {
        List<HomeModel> result = userHomeService.getUsersHomes(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot get homes dedicated to user");
    }

    public ResponseEntity createHomeForUser(Long id, HomeModel home) {
        HomeModel result = userHomeService.createHomeForUser(id, home);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot create home for user");
    }

    public ResponseEntity addExistingHome(Long id, Long idHome) {
        HomeModel result = userHomeService.addExistingHome(id, idHome);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot add existing home");
    }

    public ResponseEntity removeHomeFromUser(Long id, Long idHome) {
        if (userHomeService.removeHomeFromUser(id, idHome)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Cannot remove home from user");
    }

    public ResponseEntity getHomeByID(Long id, Long idHome) {
        HomeModel result = userHomeService.getHomeByID(id, idHome);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot get home by ID from user");
    }

    public ResponseEntity updateHome(Long id, Long idHome, HomeModel home) {
        HomeModel result = userHomeService.updateHome(id, idHome, home);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot update home for user");
    }

    public ResponseEntity updateHomeItems(Long id, Long idHome, String updatesJSON) {
        HomeModel result = userHomeService.updateHomeItems(id, idHome, updatesJSON);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body("Cannot update home for user");
    }
}
