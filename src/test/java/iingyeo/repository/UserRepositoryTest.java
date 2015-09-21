package iingyeo.repository;

import iingyeo.IingyeoTestApplication;
import iingyeo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@WebAppConfiguration
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        userRepository.deleteAll();

    }

    @Test
    public void testFindByUsername() {

        // Given
        userRepository.save(new User("taemyung", "1234"));
        userRepository.save(new User("minseo", "1234"));
        userRepository.save(new User("jungwoo", "1234"));

        for (User user : userRepository.findAll()) {
            log.info("user : {}", user);
        }

        // When
        User user = userRepository.findByUsername("taemyung");

        log.info("find user by username : {}", user);

        // Then
        assertThat(user, notNullValue());
        assertThat(user.getUsername(), is("taemyung"));

    }

}
