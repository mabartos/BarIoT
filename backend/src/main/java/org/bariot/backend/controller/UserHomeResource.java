package org.bariot.backend.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.utils.ResponseHelper;
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

import static org.bariot.backend.controller.UserResource.USER_MAPPING;

@RequestMapping(USER_MAPPING)
@RestController
@Transactional
public class UserHomeResource {
    private static final String HOME_BASIC_URL = "/{id:[\\d]+}/homes";
    private static final String HOME_ID = "/{idHome:[\\d]+}";

    @JsonIgnore
    public static final String HOME_MAPPING = UserResource.USER_MAPPING + HOME_BASIC_URL + HOME_ID;

    @Autowired
    private HomesRepository homesRepo;

    @Autowired
    private UsersRepository userRepo;

    private ResponseHelper<HomeModel, HomesRepository> helperHome;
    private ResponseHelper<UserModel, UsersRepository> helperUser;

    @PostConstruct
    public void init() {
        helperHome = new ResponseHelper<>(homesRepo);
        helperUser = new ResponseHelper<>(userRepo);
    }

    public UserHomeResource(UsersRepository userRepo, HomesRepository homesRepo) {
        helperUser = new ResponseHelper<>(userRepo);
        this.userRepo = userRepo;
        this.homesRepo = homesRepo;
    }

    // Basic operations

    @GetMapping(HOME_BASIC_URL)
    public ResponseEntity<List<HomeModel>> getUsersHomes(@PathVariable("id") Long id) {
        return helperUser.getParentsSub(id);
    }

    @PostMapping(HOME_BASIC_URL)
    public ResponseEntity<HomeModel> createHome(@PathVariable("id") Long id, @RequestBody HomeModel home) {
        HomeModel created = helperHome.create(home).getBody();
        if (created != null)
            return helperUser.addSubEntity(id, created.getId(), homesRepo);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> addExistingHome(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return helperUser.addExistingChild(id, idHome, helperHome);
    }

    @DeleteMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> removeHomeFromUser(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return helperUser.removeChildFromParent(id, idHome, homesRepo);
    }

    @DeleteMapping(HOME_BASIC_URL)
    public ResponseEntity<Void> removeAllHomesFromUser(@PathVariable("id") Long id) {
        return helperUser.removeAllChildrenFromParent(id, helperHome);
    }

    @GetMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> getHomeByID(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return helperUser.getSubByID(id, idHome, homesRepo);
    }

    @PutMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> updateHome(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome, @RequestBody HomeModel home) {
        Optional userOpt = userRepo.findById(id);
        if (userOpt.isPresent() && home != null) {
            HomeModel model = (HomeModel) helperUser.getSubByID(id, idHome, homesRepo).getBody();
            if (model != null) {
                home.setId(model.getId());
                homesRepo.save(home);
                return ResponseEntity.ok(home);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(HOME_BASIC_URL + HOME_ID)
    public ResponseEntity<HomeModel> updateHomeItems(@PathVariable("id") Long id, @PathVariable("idHome") Long
            idHome, @RequestBody Map<String, String> updates) {
        Optional userOpt = userRepo.findById(id);
        if (userOpt.isPresent()) {
            UserModel user = (UserModel) userOpt.get();
            HomeModel home = helperHome.getSubByIdFromList(idHome, user.getAllSubs()).getBody();
            home = UpdateHelper.updateItems(home, updates);
            if (home != null) {
                homesRepo.save(home);
                return ResponseEntity.ok(home);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
