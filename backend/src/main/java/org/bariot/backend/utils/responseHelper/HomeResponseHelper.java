package org.bariot.backend.utils.responseHelper;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.service.core.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HomeResponseHelper extends CRUDResponseHelper<HomeModel> {

    private HomeService homeService;

    public HomeResponseHelper(HomeService service) {
        super(service);
        this.homeService = service;
    }

    public ResponseEntity getRolesFromHome(Long homeID) {
        Set<DedicatedUserRole> roles = homeService.getRolesFromHome(homeID);
        if (roles != null)
            return ResponseEntity.ok(roles);
        return ResponseEntity.badRequest().body("Cannot get all roles");
    }

    public ResponseEntity updateUserRoleInHome(Long id, DedicatedUserRole dedicatedUserRole) {
        if (homeService.updateRoleForUser(id, dedicatedUserRole.getUser(), dedicatedUserRole.getRole()))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot update User Role in Home");
    }

    public ResponseEntity setUserRoleInHome(Long id, DedicatedUserRole dedicatedUserRole) {
        if (homeService.setRoleForUser(id, dedicatedUserRole.getUser(), dedicatedUserRole.getRole()))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot set User Role in Home");
    }

    public ResponseEntity removeUserRoleInHome(Long id, DedicatedUserRole dedicatedUserRole) {
        if (homeService.removeRoleForUser(id, dedicatedUserRole.getUser(), dedicatedUserRole.getRole()))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot remove User Role in Home");
    }
}
