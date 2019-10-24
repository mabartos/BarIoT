package org.bariot.backend.controller;


import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.repo.RoomsRepository;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.utils.ResponseHelperMulti;
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
    
    private ResponseHelperMulti<HomeModel, RoomModel> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelperMulti<>(userRepo);
    }

    // Basic operations

    @GetMapping(ROOM_BASIC_URL)
    public ResponseEntity<List<RoomModel>> getRooms(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome
    ) {
        if (helper.isInited(id, idHome)) {
            return helper.getAll();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> getRoomByID(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        if (helper.isInited(id, idHome)) {
            return helper.getItem(idRoom);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(ROOM_BASIC_URL)
    public ResponseEntity<RoomModel> createRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @RequestBody RoomModel room
    ) {
        if (helper.isInited(id, idHome)) {
            return helper.createItem(room);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> updateRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @RequestBody RoomModel room
    ) {
        if (helper.isInited(id, idHome)) {
            return helper.updateItem(idRoom, room);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> updateRoomItems(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @RequestBody Map<String, String> updates
    ) {
        if (helper.isInited(id, idHome)) {
            return helper.updateItemProps(idRoom, updates);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity<RoomModel> deleteRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        if (helper.isInited(id, idHome)) {
            return helper.deleteItemById(idRoom);
        }
        return ResponseEntity.notFound().build();
    }
}
