package org.bariot.backend;

import org.bariot.backend.controller.UserResource;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.persistence.repo.UsersRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserResourceTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserResource userResource;


    @Test
    public void basicOperations() {
        int size = usersRepository.findAll().size();
        List<UserModel> userList = userResource.getAllUsers().getBody();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size);

        UserModel testUser = new UserModel("test1", "John", "Doe");

        UserModel created = userResource.createUser(testUser).getBody();
        Assert.assertNotNull(created);
        Assert.assertEquals(testUser, created);

        userList = userResource.getAllUsers().getBody();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size + 1);

        testUser = new UserModel("test2");
        created = userResource.createUser(testUser).getBody();
        Assert.assertNotNull(created);
        Assert.assertEquals(testUser, created);

        userList = userResource.getAllUsers().getBody();

        Assert.assertNotNull(userList);
        Assert.assertEquals(userList.size(), size + 2);

        userResource.deleteUser(testUser.getId());
        Assert.assertEquals(usersRepository.findAll().size(), size + 1);


    }

}
