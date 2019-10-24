package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.RoomModel;
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

import static org.bariot.backend.controller.RoomResource.ROOM_MAPPING;
import static org.bariot.backend.controller.UserHomeResource.HOME_MAPPING;

@RequestMapping(ROOM_MAPPING)
@Transactional
@RestController
public class DeviceResource {

    private static final String DEV_BASIC_URL = "/dev";
    private static final String DEV_ID = "/{idDev:[\\d]+}";

    public static final String DEV_MAPPING = HOME_MAPPING + DEV_BASIC_URL + DEV_ID;

    @Autowired
    private UsersRepository userRepo;

    private ResponseHelperMulti<RoomModel, DeviceModel> helper;

    @PostConstruct
    public void init() {
        helper = new ResponseHelperMulti<>(userRepo);
    }
    
    // Basic operations

    @GetMapping(DEV_BASIC_URL)
    public ResponseEntity<List<DeviceModel>> getDevices(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        if (helper.isInited(id, idHome, idRoom)) {
            return helper.getAll();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity<DeviceModel> getDeviceByID(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev
    ) {
        if (helper.isInited(id, idHome, idRoom)) {
            return helper.getItem(idDev);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(DEV_BASIC_URL)
    public ResponseEntity<DeviceModel> createDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @RequestBody DeviceModel device
    ) {
        if (helper.isInited(id, idHome, idRoom)) {
            return helper.createItem(device);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity<DeviceModel> updateDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev,
            @RequestBody DeviceModel device
    ) {
        if (helper.isInited(id, idHome, idRoom)) {
            return helper.updateItem(idDev, device);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity<DeviceModel> updateDeviceItems(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev,
            @RequestBody Map<String, String> updates
    ) {
        if (helper.isInited(id, idHome, idRoom)) {
            return helper.updateItemProps(idDev, updates);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity<DeviceModel> deleteDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev
    ) {
        if (helper.isInited(id, idHome, idRoom)) {
            return helper.deleteItemById(idDev);
        }
        return ResponseEntity.notFound().build();
    }
}
