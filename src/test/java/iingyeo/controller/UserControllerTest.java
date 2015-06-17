package iingyeo.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import iingyeo.IingyeoTestApplication;
import iingyeo.entity.User;
import iingyeo.model.UserRequest;
import iingyeo.repository.UserRepository;
import iingyeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Base64;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by Taemyung on 2015-06-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Slf4j
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${local.server.port}")
    private int serverPort;

    private User user;

    private String clientId;
    private String secret;

    private String username;
    private String password;

    @Before
    public void setUp() throws Exception {

        userRepository.deleteAll();

        clientId = "iingyeo";
        secret = "1234";

        username = "tester";
        password = "1234";

        user = new User(username, password);

        userService.addUser(user);

        RestAssured.port = serverPort;

    }

    public String getAccessToken() throws Exception {

        String auth = clientId + ":" + secret;

        String authEncoded = Base64.getEncoder().encodeToString(auth.getBytes());

        // Given
        String accessToken = given()
                .header("Authorization", "Basic " + authEncoded)
                .formParam("username", username)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .formParam("scope", "read write")
                        // When
                .when()
                .post("/oauth/token")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)

                .extract()
                .path("access_token");

        log.debug("access token for user[{}] : [{}]", username, accessToken);

        return accessToken;

    }

    @Test
    public void testAddUser() throws Exception {

        // Given
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("username");
        userRequest.setPassword("1234");

        given()
                .body(userRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .post("/users")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("username", is(userRequest.getUsername()))
                .body("created", notNullValue());

    }

    @Test
    public void testGetUsers() throws Exception {

        // Given
        String accessToken = getAccessToken();

        given()
                .header("Authorization", "Bearer " + accessToken)
                        // When
                .when()
                .get("/users")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

}