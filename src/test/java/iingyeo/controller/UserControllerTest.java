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
public class UserControllerTest extends AbstractControllerTest {

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