package iingyeo.repository;

import iingyeo.IingyeoTestApplication;
import iingyeo.entity.User;
import iingyeo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        userRepository.deleteAll();

    }

    @Test
    public void testFindByEmailAddress() {

        // Given
        userRepository.save(new User("taemyung", "heo", "tmheo74@gmail.com", new Date()));
        userRepository.save(new User("minseo", "heo", "minseo@gmail.com", new Date()));
        userRepository.save(new User("jungwoo", "heo", "jungwoo@gmail.com", new Date()));

        for (User user : userRepository.findAll()) {
            log.info("user : {}", user);
        }

        // When
        User user = userRepository.findByEmailAddress("tmheo74@gmail.com");

        log.info("find user by emailAddress : {}", user);

        // Then
        assertThat(user, notNullValue());
        assertThat(user.getFirstName(), is("taemyung"));

    }

}
