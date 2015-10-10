package iingyeo.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;
import iingyeo.IingyeoTestApplication;
import iingyeo.entity.Card;
import iingyeo.model.CardRequest;
import iingyeo.model.CardResponse;
import iingyeo.service.CardService;
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
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by rainhelper on 2015. 7. 26..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Slf4j
public class CardControllerTest extends AbstractControllerTest {

    @Autowired
    private CardService cardService;

    @Test
    public void testAddCard() throws Exception {

        // Given
        String accessToken = getAccessToken();

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText("test card");
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        given()
                .header("Authorization", "Bearer " + accessToken)
                .body(cardRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .post("/cards")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("text", is(cardRequest.getText()));
    }

    @Test
    public void testAddCardWithTags() throws Exception {

        // Given
        String accessToken = getAccessToken();

        String tag1 = "tag1";
        String tag2 = "testTag";
        String tag3 = "잉여";

        StringBuilder sb = new StringBuilder();
        sb.append("test text").append("\n");
        sb.append("#").append(tag1).append(" 하하하하").append("\n");
        sb.append("#").append(tag2).append(" 하하하하").append("\n");
        sb.append("test text").append("\n");
        sb.append("iingyeo ").append("#").append(tag3).append(" 하하하하").append("\n");

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText(sb.toString());
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        given()
                .header("Authorization", "Bearer " + accessToken)
                .body(cardRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .post("/cards")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("text", is(cardRequest.getText()))
                .body("tagSet.size()", is(3))
                .body("tagSet", hasItems(tag1, tag2, tag3));
    }

    @Test
    public void testGetCard() throws Exception {

        // Given
        String accessToken = getAccessToken();

        CardResponse card = addCard();

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", card.getId())
                        // When
                .when()
                .get("/cards/{id}")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testUpdateCard() throws Exception {

        // Given
        String accessToken = getAccessToken();

        CardResponse card = addCard();
        card.setText("updated card text");
        card.setBackgroundUrl("http://updated.com/updated.png");

        CardRequest cardRequest = new CardRequest();
        cardRequest.setText(card.getText());
        cardRequest.setBackgroundUrl(card.getBackgroundUrl());

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", card.getId())
                .body(cardRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .put("/cards/{id}")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("text", is(cardRequest.getText()))
                .body("backgroundUrl", is(cardRequest.getBackgroundUrl()));
    }

    @Test
    public void testDeleteCard() throws Exception {

        // Given
        CardResponse card = addCard();

        given()
                .pathParam("id", card.getId())
                        // When
                .when()
                .delete("/cards/{id}")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testAddChildCard() throws Exception {

        // Given
        CardResponse parentCard = addCard();

        String accessToken = getAccessToken();

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText("test child card");
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        given()
                .header("Authorization", "Bearer " + accessToken)
                .body(cardRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .post("/cards/" + parentCard.getId())
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("text", is(cardRequest.getText()));

    }

    @Test
    public void testGetCardWithChild() throws Exception {

        // Given
        CardResponse parentCard = addCard();

        String accessToken = getAccessToken();

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText("test child card");
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        given()
                .header("Authorization", "Bearer " + accessToken)
                .body(cardRequest)
                .contentType(ContentType.JSON)
                        // When
                .when()
                .post("/cards/" + parentCard.getId())
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("text", is(cardRequest.getText()));

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", parentCard.getId())
                        // When
                .when()
                .get("/cards/{id}")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("childCardCount", is(1));

    }

    @Test
    public void testDeleteChildCard() throws Exception {

        // Given
        CardResponse parentCardResponse = addCard();

        String accessToken = getAccessToken();

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText("test child card");
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        ResponseBodyExtractionOptions responseBodyExtractionOptions =
                given()
                        .header("Authorization", "Bearer " + accessToken)
                        .body(cardRequest)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/cards/" + parentCardResponse.getId())
                        .then()
                        .extract().body();

        CardResponse childCard = responseBodyExtractionOptions.as(CardResponse.class);

        Card parentCard = cardService.getCard(parentCardResponse.getId());

        assertThat(parentCard.getChildCardIdList().contains(childCard.getId()), is(true));

        given()
                .pathParam("id", childCard.getId())
                        // When
                .when()
                .delete("/cards/{id}")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK);

        parentCard = cardService.getCard(parentCard.getId());

        assertThat(parentCard.getChildCardIdList().contains(childCard.getId()), is(false));

    }

    @Test
    public void testDeleteParentCard() throws Exception {

        // Given
        CardResponse parentCard = addCard();

        String accessToken = getAccessToken();

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText("test child card");
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        ResponseBodyExtractionOptions responseBodyExtractionOptions =
                given()
                        .header("Authorization", "Bearer " + accessToken)
                        .body(cardRequest)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/cards/" + parentCard.getId())
                        .then()
                        .extract().body();

        CardResponse childCardResponse = responseBodyExtractionOptions.as(CardResponse.class);

        Card childCard = cardService.getCard(childCardResponse.getId());

        assertThat(childCard.getParentCardId(), is(parentCard.getId()));

        given()
                .pathParam("id", parentCard.getId())
                        // When
                .when()
                .delete("/cards/{id}")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK);

        childCard = cardService.getCard(childCard.getId());

        assertThat(childCard.getParentCardId(), nullValue());

    }

    @Test
    public void testLikeCard() throws Exception {

        // Given
        String accessToken = getAccessToken();

        CardResponse card = addCard();

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", card.getId())
                        // When
                .when()
                .put("/cards/{id}/like")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("likeUserCount", is(1));

    }

    @Test
    public void testLikeCardWithDuplicate() throws Exception {

        // Given
        String accessToken = getAccessToken();

        CardResponse card = addCard();

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", card.getId())
                        // When
                .when()
                .put("/cards/{id}/like")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("likeUserCount", is(1));

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", card.getId())
                        // When
                .when()
                .put("/cards/{id}/like")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("likeUserCount", is(1));

    }

    @Test
    public void testGetCards() throws Exception {

        // Given
        String accessToken = getAccessToken();

        addCard();
        addCard();

        given()
                .header("Authorization", "Bearer " + accessToken)
                        // When
                .when()
                .get("/cards")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("totalCount", is(2));

    }

    @Test
    public void testGetLikeCards() throws Exception {

        // Given
        String accessToken = getAccessToken();

        CardResponse card = addCard();

        given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", card.getId())
                        // When
                .when()
                .put("/cards/{id}/like")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("likeUserCount", is(1));

        addCard();

        given()
                .header("Authorization", "Bearer " + accessToken)
                        // When
                .when()
                .get("/cards/like")
                        // Then
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("totalCount", is(1));

    }

    private CardResponse addCard() throws Exception {

        String accessToken = getAccessToken();

        CardRequest cardRequest = new CardRequest();

        cardRequest.setText("test card");
        cardRequest.setBackgroundUrl("http://test.com/test.png");

        ResponseBodyExtractionOptions responseBodyExtractionOptions =
                given()
                        .header("Authorization", "Bearer " + accessToken)
                        .body(cardRequest)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/cards")
                        .then()
                        .extract().body();

        return responseBodyExtractionOptions.as(CardResponse.class);

    }
}
