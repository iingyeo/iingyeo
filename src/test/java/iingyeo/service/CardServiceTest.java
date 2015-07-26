package iingyeo.service;

import iingyeo.IingyeoTestApplication;
import iingyeo.entity.Card;
import iingyeo.service.impl.CardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by rainhelper on 2015. 7. 26..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IingyeoTestApplication.class)
@WebAppConfiguration
@Slf4j
public class CardServiceTest {

    @Autowired
    private CardServiceImpl cardService;

    @Test
    public void testAddCard() throws Exception {

        // Given
        // When
        Card card = addCard();

        // Then
        Assert.assertNotNull(card.getId());
    }

    @Test
    public void testGetCard() throws Exception {

        // Given
        Card card = addCard();

        // When
        Card getCard = cardService.getCard(card.getId());

        // Then
        Assert.assertEquals(card.getId(), getCard.getId());
        Assert.assertEquals(card.getText(), getCard.getText());
    }

    @Test
    public void testUpdateCard() throws Exception {

        // Given
        String updateBackgroundUrl = "http://update.com/update.png";

        Card card = addCard();
        card.setBackgroundUrl(updateBackgroundUrl);

        // When
        Card updatedCard = cardService.updateCard(card);

        // Then
        Assert.assertEquals(updatedCard.getBackgroundUrl(), updateBackgroundUrl);
    }

    @Test
    public void testDeleteCard() throws Exception {

        // Given
        Card card = addCard();

        // When
        cardService.deleteCard(card.getId());

        // Then
        Assert.assertNull(cardService.getCard(card.getId()));
    }

    private Card addCard() {
        Card card = new Card();
        card.setText("test card");
        card.setBackgroundUrl("http://test.com/test.png");

        return cardService.addCard(card);
    }
}
