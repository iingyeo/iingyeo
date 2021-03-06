package iingyeo.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import iingyeo.entity.Card;
import iingyeo.model.CardListResponse;
import iingyeo.model.CardRequest;
import iingyeo.model.CardResponse;
import iingyeo.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("#oauth2.hasScope('write')")
    public CardResponse addCard(@RequestBody CardRequest cardRequest) {

        log.debug("add card request : {}", cardRequest);

        Card card = cardService.addCard(cardRequest.convertToCard());

        CardResponse cardResponse = new CardResponse(card);

        log.debug("add card response : {}", cardResponse);

        return cardResponse;

    }

    @ApiOperation(value = "Get a card by Id", notes = "Get a card by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public CardResponse getCard(@PathVariable String id) {

        log.debug("get card request for id[{}]", id);

        Card card = cardService.getCard(id);

        CardResponse cardResponse = new CardResponse(card);

        log.debug("get card response for id[{}] : {}", id, cardResponse);

        return cardResponse;

    }

    @ApiOperation(value = "Get cards", notes = "Get cards by pageNum, recordCount")
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public CardListResponse getCards(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int recordCount,
            @RequestParam(defaultValue = "created") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.debug("get cards request for pageNum[{}], recordCount[{}], sortBy[{}], sortDirection[{}]", pageNum, recordCount, sortBy, sortDirection);

        CardListResponse cardListResponse = cardService.getCards(pageNum, recordCount, sortBy, sortDirection);

        log.debug("get cards result for pageNum[{}], recordCount[{}], sortBy[{}], sortDirection[{}] : {}", pageNum, recordCount, sortBy, sortDirection, cardListResponse);

        return cardListResponse;

    }

    @ApiOperation(value = "Get like cards", notes = "Get like cards by pageNum, recordCount")
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public CardListResponse getLikeCards(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int recordCount) {

        log.debug("get like cards request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        CardListResponse cardListResponse = cardService.getLikeCards(pageNum, recordCount);

        log.debug("get like cards result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, cardListResponse);

        return cardListResponse;

    }

    @ApiOperation(value = "Get child cards", notes = "Get child cards by pageNum, recordCount")
    @RequestMapping(value = "/child", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public CardListResponse getChildCards(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int recordCount) {

        log.debug("get child cards request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        CardListResponse cardListResponse = cardService.getChildCards(pageNum, recordCount);

        log.debug("get child cards result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, cardListResponse);

        return cardListResponse;

    }

    @ApiOperation(value = "Get cards by tag", notes = "Get cards by tag, pageNum, recordCount")
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public CardListResponse getCardsByTag(@RequestParam String tagName, @RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int recordCount) {

        log.debug("get cards by tag[{}] request for pageNum[{}], recordCount[{}]", tagName, pageNum, recordCount);

        CardListResponse cardListResponse = cardService.getCardsByTag(tagName, pageNum, recordCount);

        log.debug("get cards by tag[{}] result for pageNum[{}], recordCount[{}] : {}", tagName, pageNum, recordCount, cardListResponse);

        return cardListResponse;

    }

    @ApiOperation(value = "Update a card", notes = "Update a card")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("#oauth2.hasScope('write')")
    public CardResponse updateCard(@PathVariable String id, @RequestBody CardRequest cardRequest) {

        log.debug("update card request for id[{}] : {}", id, cardRequest);

        Card card = cardRequest.convertToCard();

        card.setId(id);

        Card updatedCard = cardService.updateCard(card);

        CardResponse cardResponse = new CardResponse(updatedCard);

        log.debug("update card response for id[{}] : {}", id, cardResponse);

        return cardResponse;
    }

    @ApiOperation(value = "Delete a card by Id", notes = "Delete a card by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCard(@PathVariable String id) {

        log.debug("delete card request for id[{}]", id);

        cardService.deleteCard(id);
    }

    @ApiOperation(value = "Add a child card to parent", notes = "Add a child card to parent")
    @RequestMapping(value = "/{parentCardId}", method = RequestMethod.POST)
    @PreAuthorize("#oauth2.hasScope('write')")
    public CardResponse addChildCard(@PathVariable String parentCardId, @RequestBody CardRequest cardRequest) {

        log.debug("add child card request for parentCardId[{}] : {}", parentCardId, cardRequest);

        Card card = cardService.addChildCard(parentCardId, cardRequest.convertToCard());

        CardResponse cardResponse = new CardResponse(card);

        log.debug("add child card response for parentCardId[{}] : {}", parentCardId, cardResponse);

        return cardResponse;

    }

    @ApiOperation(value = "Like a card", notes = "Like a card")
    @RequestMapping(value = "/{id}/like", method = RequestMethod.PUT)
    @PreAuthorize("#oauth2.hasScope('write')")
    public CardResponse likeCard(@PathVariable String id) {

        log.debug("like card request for id[{}]", id);

        Card likedCard = cardService.likeCard(id);

        CardResponse cardResponse = new CardResponse(likedCard);

        log.debug("like card response for id[{}] : {}", id, cardResponse);

        return cardResponse;

    }

}
