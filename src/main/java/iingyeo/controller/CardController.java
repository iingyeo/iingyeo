package iingyeo.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import iingyeo.entity.Card;
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

    @ApiOperation(value = "Add an card", notes = "Add an card")
    @RequestMapping(method = RequestMethod.POST)
    public CardResponse addUser(@RequestBody CardRequest cardRequest) {

        log.debug("add card request : {}", cardRequest);

        Card card = cardService.addCard(cardRequest.convertToCard());

        CardResponse cardResponse = new CardResponse(card);

        log.debug("add card response : {}", cardResponse);

        return cardResponse;

    }

    @ApiOperation(value = "Get an card by Id", notes = "Get an card by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CardResponse getCard(@PathVariable String id) {

        log.debug("get card request for id[{}]", id);

        Card card = cardService.getCard(id);

        CardResponse cardResponse = new CardResponse(card);

        log.debug("get card response for id[{}] : {}", id, cardResponse);

        return cardResponse;

    }
}
