package iingyeo.service;

import iingyeo.entity.Card;
import iingyeo.model.CardListResponse;

/**
 * Created by Kang on 2015. 7. 1..
 */
public interface CardService {

    Card addCard(Card card);

    Card getCard(String id);

    CardListResponse getCards(int pageNum, int recordCount);

    void deleteCard(String id);

    Card updateCard(Card card);

    Card addChildCard(String parentCardId, Card card);

}
