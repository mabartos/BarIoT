package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.CRUDService;
import org.bariot.backend.service.core.DeviceService;
import org.bariot.backend.service.core.GeneralLayeredService;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.RoomService;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.IHasSubitems;
import org.bariot.backend.utils.Identifiable;
import org.bariot.backend.utils.UserPathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("unchecked")
@Service
public class GeneralLayeredServiceImpl<T extends Identifiable, U extends Identifiable & IHasSubitems> implements GeneralLayeredService {

    private UserPathHelper userPath;
    private UserService userService;
    private HomeService homeService;
    private RoomService roomService;
    private DeviceService deviceService;

    private CRUDService<T> getService(Identifiable model) {
        if (model != null) {
            if (model instanceof UserModel)
                return (CRUDService<T>) userService;
            else if (model instanceof HomeModel)
                return (CRUDService<T>) homeService;
            else if (model instanceof RoomModel)
                return (CRUDService<T>) roomService;
            else if (model instanceof DeviceModel)
                return (CRUDService<T>) deviceService;
        }
        return null;
    }

    @Autowired
    public void setDependency(UserService userService, HomeService homeService, RoomService roomService, DeviceService deviceService) {
        this.userPath = new UserPathHelper(userService);
        this.userService = userService;
        this.homeService = homeService;
        this.roomService = roomService;
        this.deviceService = deviceService;
    }

    @Override
    public List<T> getAllItems(Long... id) {
        U model = userPath.getPath(id);
        if (model != null && model.getAllSubs() != null)
            return model.getAllSubs();
        return null;
    }

    @Override
    public T getByID(Long... id) {
        return userPath.getPath(id);
    }

    @Override
    public Identifiable createFromJSON(String JSON, Long... id) {
        U model = userPath.getPath(id);
        T result = null;
        if (model != null && JSON != null) {
            if (model instanceof UserModel) {
                result = (T) new HomeModel();
                result = (T) homeService.createFromJSON((HomeModel) result, JSON);
            } else if (model instanceof HomeModel) {
                result = (T) new RoomModel();
                result = (T) roomService.createFromJSON((RoomModel) result, JSON, model.getID());
            } else if (model instanceof RoomModel) {
                result = (T) new DeviceModel();
                result = (T) deviceService.createFromJSON((DeviceModel) result, JSON, model.getID());
            }
            if (result != null) {
                model.addToSubSet(result);
                return result;
            }
        }
        return null;
    }

    @Override
    public T create(Identifiable entity, Long... id) {
        U model = userPath.getPath(id);
        if (model != null && entity != null) {
            T created = getService(entity).create((T) entity);
            if (created != null && model.addToSubSet(created)) {
                return created;
            }
        }
        return null;
    }

    @Override
    public Identifiable update(Identifiable entity, Long... id) {
        T model = userPath.getPath(id);
        if (model != null && entity != null) {
            return getService(entity).update(model.getID(), (T) entity);
        }
        return null;
    }

    @Override
    public T updateItems(String updatesJSON, Long... id) {
        T model = userPath.getPath(id);
        if (model != null && updatesJSON != null) {
            return getService(model).updateByProps(model.getID(), updatesJSON);
        }
        return null;
    }

    @Override
    public boolean delete(Long... id) {
        T model = userPath.getPath(id);
        if (model != null) {
            return getService(model).deleteByID(model.getID());
        }
        return false;
    }
}
