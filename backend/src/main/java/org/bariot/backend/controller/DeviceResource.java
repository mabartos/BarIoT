package org.bariot.backend.controller;

import org.bariot.backend.persistence.model.DeviceModel;
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

import static org.bariot.backend.controller.RoomResource.ROOM_MAPPING;

@RequestMapping(ROOM_MAPPING)
@Transactional
@RestController
public class DeviceResource {

    private static final String DEV_BASIC_URL = "/dev";
    private static final String DEV_ID = "/{idDev:[\\d]+}";

    public static final String DEV_MAPPING = ROOM_MAPPING + DEV_BASIC_URL + DEV_ID;

    private GeneralLayeredResponse<RoomModel> generalResponse;

    public DeviceResource(GeneralLayeredService generalService) {
        this.generalResponse = new GeneralLayeredResponse<>(generalService);
    }

    @GetMapping(DEV_BASIC_URL)
    public ResponseEntity getDevices(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom
    ) {
        return generalResponse.getAllItems(id, idHome, idRoom);
    }

    @GetMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity getDeviceByID(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev
    ) {
        return generalResponse.getByID(id, idHome, idRoom, idDev);
    }

    @PostMapping(DEV_BASIC_URL)
    public ResponseEntity createDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @RequestBody String deviceJSON
    ) {
        return generalResponse.createFromJSON(deviceJSON, id, idHome, idRoom);
    }

    @PutMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity updateDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev,
            @RequestBody DeviceModel device
    ) {
        return generalResponse.create(device, id, idHome, idRoom, idDev);
    }

    @PatchMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity updateDeviceItems(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev,
            @RequestBody String updatesJSON
    ) {
        return generalResponse.updateItems(updatesJSON, id, idHome, idRoom, idDev);

    }

    @DeleteMapping(DEV_BASIC_URL + DEV_ID)
    public ResponseEntity deleteDevice(
            @PathVariable("id") Long id,
            @PathVariable("idHome") Long idHome,
            @PathVariable("idRoom") Long idRoom,
            @PathVariable("idDev") Long idDev
    ) {
        return generalResponse.delete(id, idHome, idRoom, idDev);
    }
}
