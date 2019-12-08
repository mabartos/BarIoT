package org.bariot.backend;

import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.UserHomeService;
import org.bariot.backend.service.core.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class GeneralTest {

    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserHomeService userHomeService;

    @Autowired
    private HomeService homeService;

    private UserModel user1, user2;

    @Before
    public void setUp() {
        user1 = new UserModel("test1", "test1", "John", "Doe");
        user2 = new UserModel("test2", "test2");
    }

    @After
    public void tearDown() {
        if (user1 != null)
            usersRepository.deleteById(user1.getID());
        if (user2 != null)
            usersRepository.deleteById(user2.getID());
    }
    
    @Test
    public void basicOperations() {
        int size = usersRepository.findAll().size();
        List<UserModel> userList = userService.getAll();
        if (userList == null)
            userList = new ArrayList<>();

        Assert.assertEquals(userList.size(), size);

        UserModel created = userService.create(user1);
        Assert.assertNotNull(created);
        Assert.assertEquals(user1, created);

        userList = userService.getAll();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size + 1);

        created = userService.create(user2);
        Assert.assertNotNull(created);
        Assert.assertEquals(user2, created);

        userList = userService.getAll();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size + 2);
        UserModel byUsername = null;

    }

    private UserModel createUserInDB(UserModel user) {
        Assert.assertNotNull(user);
        UserModel userModel = userService.create(user);
        Assert.assertNotNull(userModel);
        Assert.assertEquals(userModel, user);
        return userModel;
    }

    private HomeModel createHomeInDB(HomeModel home) {
        Assert.assertNotNull(home);
        HomeModel homeCreated = homeService.create(home);
        Assert.assertNotNull(homeCreated);
        Assert.assertEquals(home, homeCreated);
        return homeCreated;
    }

    private HomeModel createHomeInDB(String name, String brokerUrl) {
        Assert.assertNotNull(name);
        HomeModel create = new HomeModel(name, brokerUrl,"sdf");
        HomeModel homeCreated = homeService.create(create);
        Assert.assertNotNull(homeCreated);
        Assert.assertEquals(create, homeCreated);
        return homeCreated;
    }

    private void addHomeToUser(UserModel user, HomeModel home) {
        Assert.assertNotNull(user);
        Assert.assertNotNull(home);
        userHomeService.addExistingHome(user1.getID(), home.getID());
        UserModel foundUser = userService.getByID(user.getID());
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

            UserModel user = userService.getByID(user1.getID());
            Assert.assertNotNull(user);
            Assert.assertEquals(1, user.getCountOfSub().intValue());

            home2 = createHomeInDB("home2", "brokerURL");
            addHomeToUser(user1, home2);

            user = userService.getByID(user1.getID());
            Assert.assertNotNull(user);
            Assert.assertEquals(2, user.getCountOfSub().intValue());

            List<HomeModel> list = userHomeService.getUsersHomes(user1.getID());
            Assert.assertNotNull(list);
            Assert.assertEquals(list.size(), user.getCountOfSub().intValue());

            user = userService.getByID(user2.getID());
            Assert.assertNotNull(user);
            Assert.assertEquals(0, user.getCountOfSub().intValue());

            //Remove home2 from user1
            userHomeService.removeHomeFromUser(user1.getID(), home1.getID());
            user = userService.getByID(user1.getID());
            Assert.assertNotNull(user);
            Assert.assertEquals(1, user.getCountOfSub().intValue());

            Assert.assertEquals("home2", user.getAllSubs().get(0).getName());

            userHomeService.removeHomeFromUser(user1.getID(), home2.getID());
            user = userService.getByID(user1.getID());
            Assert.assertNotNull(user);
            Assert.assertEquals(0, user.getCountOfSub().intValue());
        } finally {
        }

    }

    @Test
    public void deleteUpdateHomes() {
        int homeCnt = 0;
        if (homeService.getAll() != null) {
            homeCnt = homeService.getAll().size();
        }

        user1 = createUserInDB(user1);
        user2 = createUserInDB(user2);

        HomeModel home1 = createHomeInDB("home1", null);

        Assert.assertEquals(homeCnt + 1, homeService.getAll().size());

        homeService.deleteByID(home1.getID());
        if (homeService.getAll() != null) {
            Assert.assertEquals(homeCnt, homeService.getAll().size());
        }

        HomeModel home2 = createHomeInDB("home2", "10.10.10.2");
        Assert.assertEquals(homeCnt + 1, homeService.getAll().size());
        Assert.assertEquals("10.10.10.2", home2.getBrokerUrl());

        HomeModel tmpHome = new HomeModel("TMP", "BROKER_TMP","sdf");
        homeService.update(home2.getID(), tmpHome);
        home2 = homeService.getByID(home2.getID());
        Assert.assertNotNull(home2);
        Assert.assertEquals("BROKER_TMP", home2.getBrokerUrl());
        Assert.assertEquals("TMP", home2.getName());

        homeService.deleteByID(home2.getID());

        if (homeService.getAll() != null) {
            int size = homeService.getAll().size();
            Assert.assertEquals(homeCnt, size);
        }
    }
}
