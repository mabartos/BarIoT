package org.bariot.backend.service.core.impl;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.UserHomeService;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.UserService;
import org.bariot.backend.utils.UserPathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHomeServiceImpl implements UserHomeService {

    private HomeService homeService;
    private UserPathHelper userPath;

    @Autowired
    public void setDependency(UserService userService, HomeService homeService) {
        this.homeService = homeService;
        this.userPath = new UserPathHelper(userService);
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
            return home;
        }
        return null;
    }

    @Override
    public boolean removeHomeFromUser(Long id, Long idHome) {
        UserModel user = userPath.getPath(id);
        HomeModel home = homeService.getByID(idHome);
        if (user != null && home != null) {
            return user.getAllSubs().removeIf(f -> f.equals(home));
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
}
