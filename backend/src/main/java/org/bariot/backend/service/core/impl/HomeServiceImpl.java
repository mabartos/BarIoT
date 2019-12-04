package org.bariot.backend.service.core.impl;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.general.UserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.utils.UpdateHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HomeServiceImpl extends CRUDServiceSubItemsImpl<HomeModel, RoomModel> implements HomeService {
    public HomeServiceImpl(JpaRepository<HomeModel, Long> repository, UpdateHelper<HomeModel> updateHelper) {
        super(repository,updateHelper);
    }

    @Override
    public Set<DedicatedUserRole> getRolesFromHome(Long homeID) {
        HomeModel found = getByID(homeID);
        if (found != null) {
            return found.getAllUserRoles();
        }
        return null;
    }

    @Override
    public boolean setRoleForUser(Long homeID, UserModel user, UserRole role) {
        HomeModel found = getByID(homeID);
        if (found != null) {
            return found.setRoleForUser(user, role);
        }
        return false;
    }

    @Override
    public boolean updateRoleForUser(Long homeID, UserModel user, UserRole role) {
        HomeModel found = getByID(homeID);
        if (found != null) {
            return found.updateRoleForUser(user, role);
        }
        return false;
    }

    @Override
    public boolean removeRoleForUser(Long homeID, UserModel user, UserRole role) {
        HomeModel found = getByID(homeID);
        if (found != null) {
            return found.removeRoleForUser(user, role);
        }
        return false;
    }
}
