package org.bariot.backend;

import org.bariot.backend.controller.HomeResource;
import org.bariot.backend.controller.UserHomeResource;
import org.bariot.backend.controller.UserResource;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.HomesRepository;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneralTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private HomesRepository homesRepository;

    @Autowired
    private UserResource userResource;

    @Autowired
    private UserHomeResource userHomeResource;

    @Autowired
    private HomeResource homeResource;

    private UserModel user1, user2;

    @Before
    public void setUp() {
        user1 = new UserModel("test1", "John", "Doe");
        user2 = new UserModel("test2");
    }

    @After
    public void tearDown() {
        if (user1 != null)
            usersRepository.deleteById(user1.getId());
        if (user2 != null)
            usersRepository.deleteById(user2.getId());
    }

    @Test
    public void basicOperations() {
        int size = usersRepository.findAll().size();
        List<UserModel> userList = userResource.getAllUsers().getBody();
        if (userList == null)
            userList = new ArrayList<>();

        Assert.assertEquals(userList.size(), size);

        UserModel created = userResource.createUser(user1).getBody();
        Assert.assertNotNull(created);
        Assert.assertEquals(user1, created);

        userList = userResource.getAllUsers().getBody();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size + 1);

        created = userResource.createUser(user2).getBody();
        Assert.assertNotNull(created);
        Assert.assertEquals(user2, created);

        userList = userResource.getAllUsers().getBody();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size + 2);
        UserModel byUsername = null;

        try {
            byUsername = userResource.createUserByUsername("usernameTest").getBody();
        } finally {
            if (byUsername != null)
                usersRepository.deleteById(byUsername.getId());
        }
    }

    private UserModel createUserInDB(UserModel user) {
        Assert.assertNotNull(user);
        UserModel userModel = userResource.createUser(user).getBody();
        Assert.assertNotNull(userModel);
        Assert.assertEquals(userModel, user);
        return userModel;
    }

    private HomeModel createHomeInDB(HomeModel home) {
        Assert.assertNotNull(home);
        HomeModel homeCreated = homeResource.createHome(home).getBody();
        Assert.assertNotNull(homeCreated);
        Assert.assertEquals(home, homeCreated);
        return homeCreated;
    }

    private HomeModel createHomeInDB(String name, String brokerUrl) {
        Assert.assertNotNull(name);
        HomeModel create = new HomeModel(name, brokerUrl);
        HomeModel homeCreated = homeResource.createHome(create).getBody();
        Assert.assertNotNull(homeCreated);
        Assert.assertEquals(create, homeCreated);
        return homeCreated;
    }

    private void addHomeToUser(UserModel user, HomeModel home) {
        Assert.assertNotNull(user);
        Assert.assertNotNull(home);
        userHomeResource.addExistingHome(user1.getId(), home.getId());
        UserModel foundUser = userResource.getUserById(user.getId()).getBody();
        Assert.assertNotNull(foundUser);
    }

    @Test
    public void createHomes() {
        HomeModel home1 = null;
        HomeModel home2 = null;
        try {
            user1 = createUserInDB(user1);
            user2 = createUserInDB(user2);

            home1 = createHomeInDB("My_home", null);

            addHomeToUser(user1, home1);

            UserModel user = userResource.getUserById(user1.getId()).getBody();
            Assert.assertNotNull(user);
            Assert.assertEquals(1, user.getCountOfSub());

            home2 = createHomeInDB("home2", "brokerURL");
            addHomeToUser(user1, home2);

            user = userResource.getUserById(user1.getId()).getBody();
            Assert.assertNotNull(user);
            Assert.assertEquals(2, user.getCountOfSub());

            List<HomeModel> list = userHomeResource.getUsersHomes(user1.getId()).getBody();
            Assert.assertNotNull(list);
            Assert.assertEquals(list.size(), user.getCountOfSub());

            user = userResource.getUserById(user2.getId()).getBody();
            Assert.assertNotNull(user);
            Assert.assertEquals(0, user.getCountOfSub());

            //Remove home2 from user1
            userHomeResource.removeHomeFromUser(user1.getId(), home1.getId());
            user = userResource.getUserById(user1.getId()).getBody();
            Assert.assertNotNull(user);
            Assert.assertEquals(1, user.getCountOfSub());

            Assert.assertEquals("home2", user.getAllSubs().get(0).getName());

            userHomeResource.removeHomeFromUser(user1.getId(), home2.getId());
            user = userResource.getUserById(user1.getId()).getBody();
            Assert.assertNotNull(user);
            Assert.assertEquals(0, user.getCountOfSub());
        } finally {

            userHomeResource.removeAllHomesFromUser(user1.getId());
            userHomeResource.removeAllHomesFromUser(user2.getId());

            if (home1 != null)
                homesRepository.deleteById(home1.getId());
            if (home2 != null)
                homesRepository.deleteById(home2.getId());
        }

    }

    @Test
    public void deleteUpdateHomes() {
        int homeCnt = 0;
        if (homeResource.getHomes().getBody() != null) {
            homeCnt = homeResource.getHomes().getBody().size();
        }

        user1 = createUserInDB(user1);
        user2 = createUserInDB(user2);

        HomeModel home1 = createHomeInDB("home1", null);

        Assert.assertEquals(homeCnt + 1, homeResource.getHomes().getBody().size());

        homeResource.deleteHome(home1.getId());
        if (homeResource.getHomes().getBody() != null) {
            Assert.assertEquals(homeCnt, homeResource.getHomes().getBody().size());
        }

        HomeModel home2 = createHomeInDB("home2", "10.10.10.2");
        Assert.assertEquals(homeCnt + 1, homeResource.getHomes().getBody().size());
        Assert.assertEquals("10.10.10.2", home2.getBrokerUrl());

        HomeModel tmpHome = new HomeModel("TMP", "BROKER_TMP");
        homeResource.updateUser(home2.getId(), tmpHome);
        home2 = homeResource.findById(home2.getId()).getBody();
        Assert.assertNotNull(home2);
        Assert.assertEquals("BROKER_TMP", home2.getBrokerUrl());
        Assert.assertEquals("TMP", home2.getName());

        homeResource.deleteHome(home2.getId());
        Assert.assertEquals(HttpStatus.NOT_FOUND, homeResource.findById(home2.getId()).getStatusCode());

        if (homeResource.getHomes().getBody() != null) {
            int size = homeResource.getHomes().getBody().size();
            Assert.assertEquals(homeCnt, size);
        }
    }
}
