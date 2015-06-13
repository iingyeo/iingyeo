package iingyeo.service;

import iingyeo.IingyeoTestApplication;
import iingyeo.entity.User;
import iingyeo.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;


/**
 * Created by rainhelper on 2015. 6. 13..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testAddUser() throws Exception {
        // Given
        User user = new User();
        user.setFirstName("first name");
        user.setLastName("last name");
        user.setBirthDate(new Date());

        // When
        user = userService.addUser(user);

        // Then
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void testGetUser() throws Exception {
        // Given
        User user = addUser();

        // When
        User getUser = userService.getUser(user.getId());

        // Then
        Assert.assertEquals(getUser.getFirstName(), "first name");
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Given
        User user = addUser();
        user.setFirstName("updated name");

        // When
        user = userService.updateUser(user);

        // Then
        Assert.assertEquals(user.getFirstName(), "updated name");
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Given
        User user = addUser();

        // When
        userService.deleteUser(user.getId());

        // Then
        Assert.assertNull(userService.getUser(user.getId()));
    }

    public User addUser() {
        User user = new User();

        user.setFirstName("first name");
        user.setLastName("last name");
        user.setBirthDate(new Date());

        user = userService.addUser(user);

        return user;
    }
}
