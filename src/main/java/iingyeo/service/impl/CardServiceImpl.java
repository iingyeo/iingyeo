package iingyeo.service.impl;

import iingyeo.entity.Card;
import iingyeo.repository.CardRepository;
import iingyeo.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kang on 2015. 7. 1..
 */


@Service
@Slf4j
public class CardServiceImpl implements CardService{

    @Autowired
    private CardRepository cardRepository;


    @Override
    public Card addCard(Card card) {

        log.debug("add card request : {}", card);

        cardRepository.save(card);

        log.debug("add card response : {}", card);

        return card;
    }

    @Override
    public Card getCard(String id) {

        log.debug("get card request for id[{}]", id);

        Card card = cardRepository.findOne(id);

        log.debug("get card response for id[{}] : {}", id, card);

        return card;
    }
}
