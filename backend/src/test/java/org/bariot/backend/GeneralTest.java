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
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Test
    public void createHomes() {
        HomeModel home1 = null;
        HomeModel home2 = null;
        try {
            UserModel user1model = userResource.createUser(user1).getBody();
            UserModel user2model = userResource.createUser(user2).getBody();
            Assert.assertNotNull(user1model);
            Assert.assertNotNull(user2model);
            Assert.assertEquals(user1model, user1);
            Assert.assertEquals(user2model, user2);

            HomeModel home1created = new HomeModel("home1");
            home1 = homeResource.createHome(home1created).getBody();
            Assert.assertNotNull(home1);
            Assert.assertEquals(home1, home1created);
            userHomeResource.addExistingHome(user1.getId(), home1.getId());

            UserModel user = userResource.getUserById(user1.getId()).getBody();
            Assert.assertNotNull(user);
            Assert.assertEquals(1, user.getCountOfSub());

            HomeModel home2created = new HomeModel("home2", "broker.url");
            home2 = homeResource.createHome(home2created).getBody();
            Assert.assertNotNull(home2);
            Assert.assertEquals(home2, home2created);
            userHomeResource.addExistingHome(user1.getId(), home2.getId());

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
            if (user1 != null && home1 != null) {
                userHomeResource.removeAllHomesFromUser(user1.getId());
                homesRepository.deleteById(home1.getId());
            }
            if (user2 != null && home2 != null) {
                userHomeResource.removeAllHomesFromUser(user2.getId());
                homesRepository.deleteById(home2.getId());
            }
        }
    }
}
