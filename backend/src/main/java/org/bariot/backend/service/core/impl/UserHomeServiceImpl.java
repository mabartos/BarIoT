package org.bariot.backend.service.core.impl;

import org.bariot.backend.general.DedicatedUserRole;
import org.bariot.backend.general.UserRole;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.UserHomeService;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.UserPathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserHomeServiceImpl implements UserHomeService {

    private HomeService homeService;
    private UserPathHelper userPath;
    private UserService userService;

    @Autowired
    public void setDependency(UserService userService, HomeService homeService) {
        this.homeService = homeService;
        this.userPath = new UserPathHelper(userService);
        this.userService = userService;
    }

    @Override
    public List<HomeModel> getUsersHomes(Long id) {
        UserModel user = userPath.getPath(id);
        if (user != null && user.getAllSubs() != null) {
            return user.getAllSubs();
        }
        return null;
    }

    @Override
    public HomeModel createHomeForUser(Long id, HomeModel home) {
        UserModel user = userPath.getPath(id);
        HomeModel created = homeService.create(home);
        if (user != null && created != null) {
            user.addToSubSet(created);
            created.addToUsers(user);
            userService.update(user.getID(), user);
            return created;
        }
        return null;
    }

    @Override
    public HomeModel addExistingHome(Long id, Long idHome) {
        UserModel user = userPath.getPath(id);
        HomeModel home = homeService.getByID(idHome);
        if (user != null && home != null) {
            user.addToSubSet(home);
            home.addToUsers(user);
            userService.update(user.getID(), user);
            return home;
        }
        return null;
    }

    @Override
    public boolean removeHomeFromUser(Long id, Long idHome) {
        UserModel user = userPath.getPath(id);
        HomeModel home = homeService.getByID(idHome);
        if (user != null && home != null) {
            if (user.getAllSubs().removeIf(f -> f.equals(home)))
                userService.update(user.getID(), user);
        }
        return false;
    }

    @Override
    public HomeModel getHomeByID(Long id, Long idHome) {
        UserModel user = userPath.getPath(id);
        HomeModel home = homeService.getByID(idHome);
        if (user != null && home != null && user.getAllSubs().contains(home)) {
            return home;
        }
        return null;
    }

    @Override
    public HomeModel updateHome(Long id, Long idHome, HomeModel home) {
        UserModel user = userPath.getPath(id);
        if (user != null && home != null && user.getAllSubs().contains(home)) {
            HomeModel updated = homeService.update(idHome, home);
            if (updated != null)
                return updated;
        }
        return null;
    }

    @Override
    public HomeModel updateHomeItems(Long id, Long idHome, String updatesJSON) {
        UserModel user = userPath.getPath(id);
        HomeModel home = homeService.getByID(idHome);
        if (user != null && home != null && user.getAllSubs().contains(home)) {
            HomeModel updated = homeService.updateByProps(idHome, updatesJSON);
            if (updated != null)
                return updated;
        }
        return null;
    }

    @Override
    public Set<DedicatedUserRole> getRolesFromHome(Long userID, Long homeID) {
        UserModel userModel = userPath.getPath(userID);
        HomeModel homeModel = homeService.getByID(homeID);
        if (userModel != null && homeModel != null && userModel.getAllSubs().contains(homeModel)) {
            return homeService.getRolesFromHome(homeID);
        }
        return null;
    }

    @Override
    public boolean setRoleForUser(Long userID, Long homeID, UserModel user, UserRole role) {
        UserModel userModel = userPath.getPath(userID);
        HomeModel homeModel = homeService.getByID(homeID);
        if (userModel != null && homeModel != null && user != null
                && userModel.getAllSubs().contains(homeModel)) {
            return homeService.setRoleForUser(homeID, userModel, role);
        }
        return false;
    }

    @Override
    public boolean updateRoleForUser(Long userID, Long homeID, UserModel user, UserRole role) {
        UserModel userModel = userPath.getPath(userID);
        HomeModel homeModel = homeService.getByID(homeID);
        if (userModel != null && homeModel != null && user != null
                && userModel.getAllSubs().contains(homeModel)) {
            return homeService.updateRoleForUser(homeID, user, role);
        }
        return false;
    }

    @Override
    public boolean removeRoleForUser(Long userID, Long homeID, UserModel user, UserRole role) {
        UserModel userModel = userPath.getPath(userID);
        HomeModel homeModel = homeService.getByID(homeID);
        if (userModel != null && homeModel != null && user != null
                && userModel.getAllSubs().contains(homeModel)) {
            return homeService.removeRoleForUser(homeID, userModel, role);
        }
        return false;
    }
}
