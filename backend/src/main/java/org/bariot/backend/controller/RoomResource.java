package org.bariot.backend.controller;


import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.persistence.repo.RoomsRepository;
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

import static org.bariot.backend.controller.UserHomeResource.HOME_MAPPING;

@RequestMapping(HOME_MAPPING)
@Transactional
@RestController
public class RoomResource {

    private static final String ROOM_BASIC_URL = "/rooms";
    private static final String ROOM_ID = "/{idRoom:[\\d]+}";


    public static String ROOM_MAPPING = HOME_MAPPING + ROOM_BASIC_URL + ROOM_ID;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private HomesRepository homeRepo;

    @Autowired
    private RoomsRepository roomRepo;

    private UserHomeResource homeResource;
    private ResponseHelper<RoomModel, RoomsRepository> helperRoom;

    @PostConstruct
    public void init() {
        helperRoom = new ResponseHelper<>(roomRepo);
        homeResource = new UserHomeResource(userRepo, homeRepo);
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
}
