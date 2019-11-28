package org.bariot.backend.service.core;

import org.bariot.backend.persistence.model.HomeModel;

import java.util.List;

public interface UserHomeService {

    List<HomeModel> getUsersHomes(Long id);

    HomeModel createHomeForUser(Long id, HomeModel home);

    HomeModel addExistingHome(Long id, Long idHome);

    boolean removeHomeFromUser(Long id, Long idHome);

    HomeModel getHomeByID(Long id, Long idHome);

    HomeModel updateHome(Long id, Long idHome, HomeModel home);

    HomeModel updateHomeItems(Long id, Long idHome, String updatesJSON);
}
