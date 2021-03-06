package iingyeo.controller;

import com.jayway.restassured.RestAssured;
import iingyeo.IingyeoTestApplication;
import iingyeo.entity.User;
import iingyeo.repository.CardRepository;
import iingyeo.repository.TagRepository;
import iingyeo.repository.UserRepository;
import iingyeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Base64;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;

/**
 * Created by taemyung on 2015. 7. 11..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Slf4j
public abstract class AbstractControllerTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TagRepository tagRepository;

    @Value("${local.server.port}")
    protected int serverPort;

    protected User user;

    protected String clientId;
    protected String secret;

    protected String username;
    protected String password;

    @Before
    public void setUp() throws Exception {

        userRepository.deleteAll();
        cardRepository.deleteAll();
        tagRepository.deleteAll();

        clientId = "iingyeo";
        secret = "1234";

        username = "tester";
        password = "1234";

        user = new User(username, password);

        userService.addUser(user);

        RestAssured.port = serverPort;
        RestAssured.config = newConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));

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

}
