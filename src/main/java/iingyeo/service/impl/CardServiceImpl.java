package iingyeo.service.impl;

import iingyeo.entity.Card;
import iingyeo.model.CardListResponse;
import iingyeo.repository.CardRepository;
import iingyeo.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Kang on 2015. 7. 1..
 */


@Service
@Slf4j
public class CardServiceImpl implements CardService {

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

    @Override
    public CardListResponse getCards(int pageNum, int recordCount) {

        log.debug("get cards request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        Pageable pageRequest = new PageRequest(pageNum, recordCount);

        Page<Card> page = cardRepository.findAll(pageRequest);

        CardListResponse cardListResponse = new CardListResponse(page.getTotalPages(), page.getTotalElements(), pageNum, page.getContent());

        log.debug("get cards result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, cardListResponse);

        return cardListResponse;
    }
}
