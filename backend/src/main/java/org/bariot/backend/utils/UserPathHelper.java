package org.bariot.backend.utils;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SuppressWarnings("unchecked")
@Component
public class UserPathHelper {

    private UserModel user = null;
    private HomeModel home = null;
    private RoomModel room = null;
    private DeviceModel device = null;

    @Autowired
    private UsersRepository userRepo;

    public UserPathHelper() {
    }

    private <Parent extends IbasicInfo, Child extends IbasicInfo> Child getFromList(Parent parent, long id) {
        if (parent == null)
            return null;
        for (Object child : parent.getAllSubs()) {
            if (((Child) child).getId().equals(id))
                return (Child) child;
        }
        return null;
    }

    private UserModel getUser(long id) {
        Optional opt = userRepo.findById(id);
        if (opt.isPresent()) {
            return (UserModel) opt.get();
        } else
            return null;
    }

    public <Model extends IbasicInfo> Model getPath(Long... ids) {
        UserModel user = null;
        HomeModel home = null;
        RoomModel room = null;
        DeviceModel device = null;

        Model last = null;
        for (int i = 0; i < ids.length; i++) {
            switch (i) {
                case 0:
                    user = getUser(ids[i]);
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
