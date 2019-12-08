package org.bariot.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bariot.backend.general.RoomType;
import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.CRUDService;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.RoomService;
import org.bariot.backend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
public class UpdateHelper<Model extends Identifiable> {

    private UserService userService;
    private HomeService homeService;
    private RoomService roomService;

    @Autowired
    public void setDependency(@Lazy UserService userService, HomeService homeService, RoomService roomService) {
        this.userService = userService;
        this.homeService = homeService;
        this.roomService = roomService;
    }

    public Model updateItems(Model model, String updatesJSON) {
        return updateItems(model, updatesJSON, null);
    }

    /**
     * Main function to update Items in model
     *
     * @param model   data, which will be overridden
     * @param updatesJSON new values
     * @return new Model class with edited parameters
     */
    public Model updateItems(Model model, String updatesJSON, Long parentID) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> updatesWithInteger = mapper.readValue(updatesJSON, Map.class);
            Map<String, String> updates = new HashMap<>();
            updatesWithInteger.forEach((key, value) -> updates.put(key, String.valueOf(value)));

            if (model != null && !updates.isEmpty()) {
                if (model instanceof UserModel) {
                    return updateUser(model, updates);
                } else if (model instanceof HomeModel) {
                    return updateHome(model, updates);
                } else if (model instanceof RoomModel) {
                    return updateRoom(model, updates, parentID);
                } else if (model instanceof DeviceModel) {
                    return updateDevice(model, updates, parentID);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update UserModel items
     */
    private Model updateUser(Model model, Map<String, String> updates) {
        UserModel res = (UserModel) model;
        updates.forEach((key, val) -> {
            switch (key) {
                case "firstname":
                    res.setFirstname(val);
                    break;
                case "lastname":
                    res.setLastname(val);
                    break;

            }
                }
        );
        return (Model) res;
    }

    /**
     * Update HomeModel items
     */
    private Model updateHome(Model model, Map<String, String> updates) {
        HomeModel res = (HomeModel) model;
        updates.forEach((key, val) -> {
            switch (key) {
                case "name":
                    res.setName(val);
                    break;
                case "brokerUrl":
                    res.setBrokerUrl(val);
                    break;
                case "image":
                    res.setImage(val);
                    break;
            }
        });
        return (Model) res;
    }

    /**
     * Update RoomModel items
     */
    private Model updateRoom(Model model, Map<String, String> updates, Long parentID) {
        RoomModel res = (RoomModel) model;
        updates.forEach((key, val) -> {
            switch (key) {
                case "name":
                    res.setName(val);
                    break;
                case "type":
                    res.setType(RoomType.values()[Integer.parseInt(val)]);
                case "image":
                    res.setImage(val);
                    break;
            }

            if (parentID != null) {
                res.setHome((HomeModel) getByID(parentID.toString(), (CRUDService<Model>) homeService));
            }
                }
        );
        return (Model) res;
    }

    /**
     * Update DeviceModel items
     */
    private Model updateDevice(Model model, Map<String, String> updates, Long parentID) {
        DeviceModel res = (DeviceModel) model;
        updates.forEach((key, val) -> {
            switch (key) {
                case "name":
                    res.setName(val);
                    break;
            }
            if (parentID != null) {
                res.setRoom((RoomModel) getByID(parentID.toString(), (CRUDService<Model>) roomService));
            }
                }
        );
        return (Model) res;
    }

    private Model getByID(String value, CRUDService<Model> service) {
        return getByID(value, service, null);
    }

    //TODO role
    private Model getByID(String value, CRUDService<Model> service, Integer minRole) {
        try {
            if (value == null || service == null || value.equals("null"))
                return null;
            long id = Long.parseLong(value);
            return service.getByID(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
