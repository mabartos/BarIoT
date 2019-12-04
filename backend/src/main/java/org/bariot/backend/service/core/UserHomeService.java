package org.bariot.backend.service.core;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.general.UserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;

import java.util.List;
import java.util.Set;

public interface UserHomeService {

    List<HomeModel> getUsersHomes(Long id);

    HomeModel createHomeForUser(Long id, HomeModel home);

    HomeModel addExistingHome(Long id, Long idHome);

    boolean removeHomeFromUser(Long id, Long idHome);

    HomeModel getHomeByID(Long id, Long idHome);

    HomeModel updateHome(Long id, Long idHome, HomeModel home);

    HomeModel updateHomeItems(Long id, Long idHome, String updatesJSON);

    Set<DedicatedUserRole> getRolesFromHome(Long userID, Long homeID);

    boolean setRoleForUser(Long userID, Long homeID, UserModel user, UserRole role);

    boolean updateRoleForUser(Long userID, Long homeID, UserModel user, UserRole role);

    boolean removeRoleForUser(Long userID, Long homeID, UserModel user, UserRole role);
}
