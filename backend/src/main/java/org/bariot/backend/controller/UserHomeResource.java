package org.bariot.backend.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.HomesRepository;
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
import java.util.Objects;
import java.util.stream.Collectors;

import static org.bariot.backend.controller.UserResource.USER_MAPPING;


@RequestMapping(USER_MAPPING)
@RestController
@Transactional
public class UserHomeResource {
    private static final String HOME_BASIC_URL = "/{id:[\\d]+}/homes";

    @JsonIgnore
    public static final String HOME_MAPPING = UserResource.USER_MAPPING + HOME_BASIC_URL;


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

    // Basic operations

    @GetMapping(HOME_BASIC_URL)
    public List<HomeModel> getHomes(@PathVariable("id") long id) {
        return Objects.requireNonNull(helperUser.getParentsSub(id).getBody())
                .stream().map(f -> (HomeModel) f)
                .collect(Collectors.toList());
    }

    @PostMapping(HOME_BASIC_URL)
    public ResponseEntity<HomeModel> createHome(@PathVariable("id") Long id, @RequestBody HomeModel home) {
        HomeModel created = helperHome.create(home).getBody();
        if (created != null)
            return helperUser.addSubEntity(id, created.getId(), homesRepo);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping(HOME_BASIC_URL + "/{idHome:[\\d]+]}")
    public ResponseEntity<HomeModel> removeHomeFromUser(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        return helperUser.removeChildFromParent(id, idHome, homesRepo);
    }


/*



    // GET

    @GetMapping("{id:[\\d]+}/homes")
    public String get(@PathVariable("id") int id) {
        return "DEBUG";
        Optional opt = user.getAllSubs().stream().filter(f -> f.getId() == id).findFirst();
        if (opt.isPresent()) {
            return (HomeModel) opt.get();
        }
        return null;


    }



    @GetMapping("/ss/{idOrName}")
    public ResponseEntity<HomeModel> getHomeByIdOrName(@PathVariable("idOrName") String idOrName) {
        return helper.getByIdOrName(idOrName);
    }


    @GetMapping("/{id:[\\d]+}/delete/{idOrName}")
    public ResponseEntity<HomeModel> deleteHome(@PathVariable("idOrName") String idOrName) {
        return helper.deleteByIdOrName(idOrName);
    }


    @GetMapping("/gethomes")
    public ResponseEntity<List<HomeModel>> getHomes() {
        return helper.getAllSub(homesRepo);
    }



    @GetMapping("/{id:[\\d]+}/homess")
    public ResponseEntity<List<HomeModel>> getUsersHomes(@PathVariable("id") Long id) {
        return helper.getParentsSub(id);
    }


    @GetMapping("/{id:[\\d]+}/homes/*")
    public UserHomeResource getHomess(@PathVariable("id") Long id) {
        return new UserHomeResource(usersRepository.getOne(id));
    }

    @GetMapping("/{idParent}/addHome/{idChild}")
    public ResponseEntity<HomeModel> addHome(@PathVariable("idParent") String idParent, @PathVariable("idChild") String idChild) {
        return helper.addSubEntity(Long.parseLong(idParent), Long.parseLong(idChild), homesRepo);
    }
*/
}
