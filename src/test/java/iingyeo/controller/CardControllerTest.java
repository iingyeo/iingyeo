package iingyeo.controller;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;
import iingyeo.IingyeoTestApplication;
import iingyeo.entity.Card;
import iingyeo.model.CardRequest;
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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
    CardService cardService;

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
    public void testGetCard() throws Exception {

        // Given
        String accessToken = getAccessToken();

        Card card = addCard();

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

        Card card = addCard();
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
        Card card = addCard();

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
        Card parentCard = addCard();

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
    public void testDeleteChildCard() throws Exception {

        // Given
        Card parentCard = addCard();

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

        Card childCard = responseBodyExtractionOptions.as(Card.class);

        parentCard = cardService.getCard(parentCard.getId());

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
        Card parentCard = addCard();

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

        Card childCard = responseBodyExtractionOptions.as(Card.class);

        childCard = cardService.getCard(childCard.getId());

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

    private Card addCard() throws Exception {

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

        return responseBodyExtractionOptions.as(Card.class);
    }
}
