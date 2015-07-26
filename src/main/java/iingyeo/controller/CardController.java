package iingyeo.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import iingyeo.entity.Card;

import iingyeo.model.CardListResponse;
import iingyeo.model.CardRequest;
import iingyeo.model.CardResponse;
import iingyeo.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kang on 2015. 7. 1..
 */
@RestController
@RequestMapping(value = "/cards")
@Slf4j
public class CardController {

    @Autowired
    private CardService cardService;

    @ApiOperation(value = "Add a card", notes = "Add a card")
    @RequestMapping(method = RequestMethod.POST)
    public CardResponse addCard(@RequestBody CardRequest cardRequest) {

        log.debug("add card request : {}", cardRequest);

        Card card = cardService.addCard(cardRequest.convertToCard());

        CardResponse cardResponse = new CardResponse(card);

        log.debug("add card response : {}", cardResponse);

        return cardResponse;

    }

    @ApiOperation(value = "Get a card by Id", notes = "Get a card by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CardResponse getCard(@PathVariable String id) {

        log.debug("get card request for id[{}]", id);

        Card card = cardService.getCard(id);

        CardResponse cardResponse = new CardResponse(card);

        log.debug("get card response for id[{}] : {}", id, cardResponse);

        return cardResponse;

    }

    @ApiOperation(value = "Get cards", notes = "Get cards by pageNum, recordCount")
    @RequestMapping(method = RequestMethod.GET)
    public CardListResponse getCards(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int recordCount) {

        log.debug("get cards request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        CardListResponse cardListResponse = cardService.getCards(pageNum, recordCount);

        log.debug("get cards result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, cardListResponse);

        return cardListResponse;

    }

    @ApiOperation(value = "Update a card", notes = "Update a card")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CardResponse updateCard(@PathVariable String id, @RequestBody CardRequest cardRequest) {

        log.debug("update card request for id[{}] : {}", id, cardRequest);

        Card card = cardRequest.convertToCard();

        card.setId(id);

        Card updatedCard = cardService.updateCard(card);

        CardResponse cardResponse = new CardResponse(card);

        log.debug("update card response for id[{}] : {}", id, cardResponse);

        return cardResponse;
    }

    @ApiOperation(value = "Delete a card by Id", notes = "Delete a card by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCard(@PathVariable String id) {

        log.debug("delete card request for id[{}]", id);

        cardService.deleteCard(id);
    }

}
