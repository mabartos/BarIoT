package org.bariot.backend.service.core;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.general.UserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;

import java.util.Set;

public interface HomeService extends CRUDServiceSubItems<HomeModel, RoomModel> {

    Set<DedicatedUserRole> getRolesFromHome(Long homeID);

    boolean setRoleForUser(Long homeID, UserModel user, UserRole role);

    boolean updateRoleForUser(Long homeID, UserModel user, UserRole role);

    boolean removeRoleForUser(Long homeID, UserModel user, UserRole role);

}
