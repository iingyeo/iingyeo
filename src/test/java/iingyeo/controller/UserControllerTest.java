package iingyeo.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import iingyeo.IingyeoTestApplication;
import iingyeo.model.UserRequest;
import iingyeo.repository.UserRepository;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Taemyung on 2015-06-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp() throws Exception {

        userRepository.deleteAll();

        RestAssured.port = serverPort;

    }

    @Test
    public void testAddUser() throws Exception {

        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("firstName1");
        userRequest.setLastName("lastName1");
        userRequest.setEmailAddress("tester@test.com");
        userRequest.setBirthDate(new Date());

        given()
                .body(userRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .post("/users")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("firstName", is(userRequest.getFirstName()))
                .body("emailAddress", is(userRequest.getEmailAddress()));

    }
}