package org.bariot.backend.controller;


import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.persistence.repo.RoomsRepository;
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

import static org.bariot.backend.controller.UserHomeResource.HOME_MAPPING;

@RequestMapping(HOME_MAPPING)
@Transactional
@RestController
public class RoomResource {

    private static final String ROOM_BASIC_URL = "/rooms";
    private static final String ROOM_ID = "/{idRoom:[\\d]+}";

    public static final String ROOM_MAPPING = HOME_MAPPING + ROOM_BASIC_URL + ROOM_ID;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private HomesRepository homeRepo;

    @Autowired
    private RoomsRepository roomRepo;

    @Autowired
    private UserHomeResource userHomeResource;


    private UserHomeResource homeResource;
    private ResponseHelper<RoomModel, RoomsRepository> helperRoom;

    @PostConstruct
    public void init() {
        helperRoom = new ResponseHelper<>(roomRepo);
        homeResource = new UserHomeResource(userRepo, homeRepo);
    }

    public RoomResource(UsersRepository userRepo, HomesRepository homesRepo, RoomsRepository roomsRepo) {
        helperRoom = new ResponseHelper<>(roomsRepo);
        this.userRepo = userRepo;
        this.homeRepo = homesRepo;
        this.roomRepo = roomsRepo;
    }

    // Basic operations

    @GetMapping(ROOM_BASIC_URL)
    public ResponseEntity<List<RoomModel>> getRooms(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome) {
        HomeModel home = homeResource.getHomeByID(id, idHome).getBody();
        if (home != null) {
            List<RoomModel> roomList = home.getRoomsList();
            if (roomList != null)
                return ResponseEntity.ok(home.getRoomsList());
            else
                return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> getRoomByID(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        HomeModel home = homeResource.getHomeByID(id, idHome).getBody();
        if (home != null) {
            RoomModel room = helperRoom.getSubByIdFromList(idRoom, home.getRoomsList()).getBody();
            if (room != null) {
                return ResponseEntity.ok(room);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(ROOM_BASIC_URL)
    public ResponseEntity<RoomModel> createRoom(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome, @RequestBody RoomModel room) {
        HomeModel home = homeResource.getHomeByID(id, idHome).getBody();
        if (home != null) {
            List<RoomModel> roomList = home.getRoomsList();
            if (roomList != null) {
                roomList.add(room);
                return ResponseEntity.ok(room);
            }
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> addExistingRoom(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome, @PathVariable("idRoom") Long idRoom) {
        HomeModel home = userHomeResource.getHomeByID(id, idHome).getBody();
        Optional opt = roomRepo.findById(idRoom);
        if (home != null && opt.isPresent()) {
            RoomModel room = (RoomModel) opt.get();
            home.getRoomsList().add(room);
            return ResponseEntity.ok(room);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> updateRoom(@PathVariable("id") Long id, @PathVariable("idHome") Long idHome, @PathVariable("idRoom") Long idRoom, @RequestBody RoomModel room) {
        HomeModel home = userHomeResource.getHomeByID(id, idHome).getBody();
        if (home != null && room != null) {
            RoomModel model = helperRoom.getSubByIdFromList(idRoom, home.getRoomsList()).getBody();
            if (model != null) {
                room.setId(model.getId());
                roomRepo.save(room);
                return ResponseEntity.ok(room);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> updateRoomItems(@PathVariable("id") Long id, @PathVariable("idHome") Long
            idHome, @PathVariable("idRoom") Long idRoom, @RequestBody Map<String, String> updates) {
        HomeModel home = userHomeResource.getHomeByID(id, idHome).getBody();
        if (home != null) {
            RoomModel room = helperRoom.getSubByIdFromList(idRoom, home.getRoomsList()).getBody();
            room = UpdateHelper.updateItems(room, updates);
            if (room != null) {
                roomRepo.save(room);
                return ResponseEntity.ok(room);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> removeRoomFromHome(@PathVariable("id") Long id, @PathVariable("idHome") Long
            idHome, @PathVariable("idRoom") Long idRoom) {
        HomeModel home = homeResource.getHomeByID(id, idHome).getBody();
        if (home != null) {
            RoomModel room = helperRoom.getSubByIdFromList(idRoom, home.getRoomsList()).getBody();
            if (room != null) {
                if (home.getRoomsList().remove(room)) {
                    return ResponseEntity.ok(room);
                }
            }
        }
            return ResponseEntity.notFound().build();
    }


}
