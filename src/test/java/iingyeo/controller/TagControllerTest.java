package iingyeo.controller;

import iingyeo.IingyeoTestApplication;
import iingyeo.entity.Tag;
import iingyeo.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by taemyung on 2015. 10. 10..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Slf4j
public class TagControllerTest extends AbstractControllerTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testGetTags() throws Exception {

        // Given
        String accessToken = getAccessToken();

        for (int i = 1; i <= 10; i++) {
            addTag("tag" + i);
        }

        given()
                .header("Authorization", "Bearer " + accessToken)
                        // When
                .when()
                .get("/tags")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("totalCount", is(10));

    }

    private Tag addTag(String name) {
        return tagRepository.save(new Tag(name));
    }

}
