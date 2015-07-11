package iingyeo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by taemyung on 2015. 7. 11..
 */
@Slf4j
public class AuthenticationControllerTest extends AbstractControllerTest {

    @Test
    public void testGetLoggedInUser() throws Exception {

        // Given
        String accessToken = getAccessToken();

        given()
                .header("Authorization", "Bearer " + accessToken)
                        // When
                .when()
                .get("/user")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body("name", is(username));

    }

}
