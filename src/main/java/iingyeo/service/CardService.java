package iingyeo.service;

import iingyeo.entity.Card;

/**
 * Created by Kang on 2015. 7. 1..
 */
public interface CardService {

    public Card addCard(Card card);

    public Card getCard(String id);

}
