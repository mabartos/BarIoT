package org.bariot.backend.utils;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("unchecked")
@Service
public class UserPathHelper {

    private UserService userService;

    public UserPathHelper(UserService userService) {
        this.userService = userService;
    }

    private <Parent extends IBasicInfo & Identifiable, Child extends IBasicInfo & Identifiable> Child getFromList(Parent parent, long id) {
        if (parent == null)
            return null;
        Optional childOpt = parent.getAllSubs().stream().filter(f -> ((Child) f).getID() == id).findFirst();
        if (childOpt.isPresent())
            return (Child) childOpt.get();
        return null;
    }


    public <Model extends IBasicInfo> Model getPath(Long... ids) {
        UserModel user = null;
        HomeModel home = null;
        RoomModel room = null;
        DeviceModel device = null;

        Model last = null;
        for (int i = 0; i < ids.length; i++) {
            switch (i) {
                case 0:
                    user = userService.getByID(ids[i]);
                    if (user == null)
                        return null;
                    last = (Model) user;
                    break;
                case 1:
                    home = getFromList(user, ids[i]);
                    if (home == null)
                        return null;
                    last = (Model) home;
                    break;
                case 2:
                    room = getFromList(home, ids[i]);
                    if (room == null)
                        return null;
                    last = (Model) room;
                    break;
                case 3:
                    device = getFromList(room, ids[i]);
                    if (device == null)
                        return null;
                    last = (Model) device;
                    break;
                default:
                    break;
            }
        }
        return last;
    }
}
