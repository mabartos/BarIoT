package org.bariot.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
public class UpdateHelper {

    /**
     * Main function to update Items in model
     *
     * @param model   data, which will be overridden
     * @param updatesJSON new values
     * @param <Model> Generics Model class
     * @return new Model class with edited parameters
     */
    public static <Model extends Identifiable> Model updateItems(Model model, String updatesJSON) {
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
                    return updateRoom(model, updates);
                } else if (model instanceof DeviceModel) {
                    return updateDevice(model, updates);
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
    private static <Model extends Identifiable> Model updateUser(Model model, Map<String, String> updates) {
        UserModel res = (UserModel) model;
        updates.forEach((key, val) -> {
                    if (key.equals("firstName")) {
                        res.setFirstName(val);
                    } else if (key.equals("lastName")) {
                        res.setLastName(val);
                    }
                }
        );
        return (Model) res;
    }

    /**
     * Update HomeModel items
     */
    private static <Model extends Identifiable> Model updateHome(Model model, Map<String, String> updates) {
        HomeModel res = (HomeModel) model;
        updates.forEach((key, val) -> {
                    if (key.equals("name")) {
                        res.setName(val);
                    } else if (key.equals("brokerUrl")) {
                        res.setBrokerUrl(val);
                    }
                }
        );
        return (Model) res;
    }

    /**
     * Update RoomModel items
     */
    private static <Model extends Identifiable> Model updateRoom(Model model, Map<String, String> updates) {
        RoomModel res = (RoomModel) model;
        updates.forEach((key, val) -> {
                    if (key.equals("name")) {
                        res.setName(val);
                    }
                }
        );
        return (Model) res;
    }

    /**
     * Update DeviceModel items
     */
    private static <Model extends Identifiable> Model updateDevice(Model model, Map<String, String> updates) {
        DeviceModel res = (DeviceModel) model;
        updates.forEach((key, val) -> {
                    if (key.equals("name")) {
                        res.setName(val);
                    }
                }
        );
        return (Model) res;
    }

}
