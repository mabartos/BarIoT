package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.repo.DevicesRepository;
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
    @Autowired
    private HomesRepository homeRepo;
    @Autowired
    private RoomsRepository roomRepo;

    @Autowired
    private DevicesRepository devRepo;

    private RoomResource roomResource;

    private ResponseHelper<DeviceModel, DevicesRepository> helperDevice;

    @PostConstruct
    public void init() {
        helperDevice = new ResponseHelper<>(devRepo);
        roomResource = new RoomResource(userRepo, homeRepo, roomRepo);
    }
    
    // Basic operations

    @GetMapping(DEV_BASIC_URL)
    public ResponseEntity<List<DeviceModel>> getDevices(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        if (room != null) {
            List<DeviceModel> devList = room.getListDevices();
            if (devList != null)
                return ResponseEntity.ok(devList);
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
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        if (room != null) {
            DeviceModel device = helperDevice.getSubByIdFromList(idDev, room.getListDevices()).getBody();
            if (device != null) {
                return ResponseEntity.ok(device);
            }
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
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        if (room != null) {
            List<DeviceModel> deviceList = room.getListDevices();
            if (deviceList != null) {
                deviceList.add(device);
                return ResponseEntity.ok(device);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity<DeviceModel> addExistingDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev
    ) {
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        Optional opt = devRepo.findById(idDev);
        if (room != null && opt.isPresent()) {
            DeviceModel device = (DeviceModel) opt.get();
            room.getListDevices().add(device);
            return ResponseEntity.ok(device);
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
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        if (room != null && device != null) {
            DeviceModel model = helperDevice.getSubByIdFromList(idDev, room.getListDevices()).getBody();
            if (model != null) {
                helperDevice.update(idDev, model);
                return ResponseEntity.ok(device);
            }
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
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        if (room != null) {
            DeviceModel device = helperDevice.getSubByIdFromList(idDev, room.getListDevices()).getBody();
            device = UpdateHelper.updateItems(device, updates);
            if (device != null) {
                devRepo.save(device);
                return ResponseEntity.ok(device);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity<DeviceModel> removeDeviceFromRoom(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev
    ) {
        RoomModel room = roomResource.getRoomByID(id, idHome, idRoom).getBody();
        if (room != null) {
            DeviceModel device = helperDevice.getSubByIdFromList(idRoom, room.getListDevices()).getBody();
            if (device != null) {
                if (room.getListDevices().remove(device)) {
                    return ResponseEntity.ok(device);
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

}
