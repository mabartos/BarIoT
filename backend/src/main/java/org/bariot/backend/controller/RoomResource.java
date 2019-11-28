package org.bariot.backend.controller;


import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.service.core.GeneralLayeredService;
import org.bariot.backend.utils.responseHelper.GeneralLayeredResponse;
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

import javax.transaction.Transactional;

import static org.bariot.backend.controller.UserHomeResource.HOME_MAPPING;

@RequestMapping(HOME_MAPPING)
@Transactional
@RestController
public class RoomResource {

    private static final String ROOM_BASIC_URL = "/rooms";
    private static final String ROOM_ID = "/{idRoom:[\\d]+}";

    public static final String ROOM_MAPPING = HOME_MAPPING + ROOM_BASIC_URL + ROOM_ID;

    private GeneralLayeredResponse<RoomModel> generalResponse;

    public RoomResource(GeneralLayeredService generalService) {
        this.generalResponse = new GeneralLayeredResponse<>(generalService);
    }
    // Basic operations

    @GetMapping(ROOM_BASIC_URL)
    public ResponseEntity getRooms(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome
    ) {
        return generalResponse.getAllItems(id, idHome);
    }

    @GetMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity getRoomByID(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        return generalResponse.getByID(id, idHome, idRoom);
    }

    @PostMapping(ROOM_BASIC_URL)
    public ResponseEntity createRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @RequestBody String roomJSON
    ) {
        return generalResponse.createFromJSON(roomJSON, id, idHome);
    }

    @PutMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity updateRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @RequestBody RoomModel room
    ) {
        return generalResponse.update(room, id, idHome, idRoom);
    }

    @PatchMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity updateRoomItems(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @RequestBody String updatesJSON
    ) {
        return generalResponse.updateItems(updatesJSON, id, idHome, idRoom);
    }

    @DeleteMapping(ROOM_BASIC_URL + ROOM_ID)
    public ResponseEntity deleteRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        return generalResponse.delete(id, idHome, idRoom);
    }
}
