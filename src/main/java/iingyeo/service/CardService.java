package iingyeo.service;

import iingyeo.entity.Card;
import iingyeo.model.CardListResponse;

/**
 * Created by Kang on 2015. 7. 1..
 */
public interface CardService {

    public Card addCard(Card card);

    public Card getCard(String id);

    public CardListResponse getCards(int pageNum, int recordCount);
}
