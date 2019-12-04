package org.bariot.backend.utils.responseHelper;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.UserHomeService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class UserHomeResponseHelper {

    private UserHomeService userHomeService;
    private HomeService homeService;

    public UserHomeResponseHelper(UserHomeService userHomeService, HomeService homeService) {
        this.userHomeService = userHomeService;
        this.homeService = homeService;
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

    public ResponseEntity getRolesFromHome(Long userID, Long homeID) {
        Set<DedicatedUserRole> roles = userHomeService.getRolesFromHome(userID, homeID);
        if (roles != null)
            return ResponseEntity.ok(roles);
        return ResponseEntity.badRequest().body("Cannot get all roles in home");
    }

    public ResponseEntity updateUserRoleInHome(Long userID, Long homeID, DedicatedUserRole dedicatedUserRole) {
        if (userHomeService.updateRoleForUser(userID, homeID, dedicatedUserRole.getUser(), dedicatedUserRole.getRole()))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot update roles in home");
    }

    public ResponseEntity setUserRoleInHome(Long userID, Long homeID, DedicatedUserRole dedicatedUserRole) {
        if (userHomeService.setRoleForUser(userID, homeID, dedicatedUserRole.getUser(), dedicatedUserRole.getRole()))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot set roles in home");
    }

    public ResponseEntity removeUserRoleInHome(Long userID, Long homeID, DedicatedUserRole dedicatedUserRole) {
        if (userHomeService.removeRoleForUser(userID, homeID, dedicatedUserRole.getUser(), dedicatedUserRole.getRole()))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot remove roles in home");
    }
}
