package iingyeo.service.impl;

import iingyeo.entity.Card;
import iingyeo.entity.Tag;
import iingyeo.entity.User;
import iingyeo.model.CardListResponse;
import iingyeo.repository.CardRepository;
import iingyeo.service.CardService;
import iingyeo.service.TagService;
import iingyeo.service.UserService;
import iingyeo.util.IingyeoBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

import static iingyeo.repository.predicate.CardPredicate.findLikeCardsByUserId;

/**
 * Created by Kang on 2015. 7. 1..
 */
@Service
@Slf4j
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Override
    public Card addCard(Card card) {

        log.debug("add card request : {}", card);

        User loggedInUser = userService.getLoggedInUser();

        if (loggedInUser != null) {
            card.setUserId(loggedInUser.getId());
        }

        Set<String> tagNames = card.filterTags();

        for (String tagName : tagNames) {
            Tag tag = tagService.findByName(tagName);
            card.addTagId(tag.getId());
        }

        card.setCreated(new Date());
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

        Pageable pageRequest = new PageRequest(pageNum, recordCount, Sort.Direction.DESC, "created");

        Page<Card> page = cardRepository.findAll(pageRequest);

        CardListResponse cardListResponse = new CardListResponse(page.getTotalPages(), page.getTotalElements(), pageNum, page.getContent());

        log.debug("get cards result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, cardListResponse);

        return cardListResponse;
    }

    @Override
    public CardListResponse getLikeCards(int pageNum, int recordCount) {

        log.debug("get like cards request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        Pageable pageRequest = new PageRequest(pageNum, recordCount, Sort.Direction.DESC, "created");

        User loggedInUser = userService.getLoggedInUser();

        Page<Card> page = cardRepository.findAll(findLikeCardsByUserId(loggedInUser.getId()), pageRequest);

        CardListResponse cardListResponse = new CardListResponse(page.getTotalPages(), page.getTotalElements(), pageNum, page.getContent());

        log.debug("get like cards result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, cardListResponse);

        return cardListResponse;

    }

    @Override
    public Card updateCard(Card card) {

        log.debug("update card request : {}", card);

        Card targetCard = cardRepository.findOne(card.getId());

        IingyeoBeanUtils.copyNotNullProperties(card, targetCard);

        Set<String> tagNames = targetCard.filterTags();

        targetCard.getTagIdSet().clear();

        for (String tagName : tagNames) {
            Tag tag = tagService.findByName(tagName);
            targetCard.addTagId(tag.getId());
        }

        targetCard.setUpdated(new Date());

        Card updatedCard = cardRepository.save(targetCard);

        log.debug("update card response : {}", updatedCard);

        return updatedCard;
    }

    @Override
    public Card addChildCard(String parentCardId, Card card) {

        log.debug("add child card request for parentCardId[{}] : {}", parentCardId, card);

        card.setParentCardId(parentCardId);

        card = addCard(card);

        Card parentCard = getCard(parentCardId);

        parentCard.addChildCardId(card.getId());
        updateCard(parentCard);

        log.debug("add child card response for parentCardId[{}] : {}", parentCardId, card);

        return card;

    }

    @Override
    public Card likeCard(String id) {

        log.debug("like card request for id[{}]", id);

        Card targetCard = cardRepository.findOne(id);

        User loggedInUser = userService.getLoggedInUser();

        if (loggedInUser != null) {
            if (!targetCard.like(loggedInUser.getId())) {
                log.debug("user[{}] already liked this card[{}]", loggedInUser.getId(), id);
            }
        }

        Card updatedCard = cardRepository.save(targetCard);

        log.debug("like card response : {}", updatedCard);

        return updatedCard;

    }

    @Override
    public void deleteCard(String id) {

        log.debug("delete card request by id[{}]", id);

        Card card = cardRepository.findOne(id);

        if (card.getParentCardId() != null) {

            log.debug("remove this card from parent card[{}]", card.getParentCardId());

            Card parentCard = cardRepository.findOne(card.getParentCardId());
            parentCard.getChildCardIdList().remove(card.getId());
            cardRepository.save(parentCard);

        }

        if (!card.getChildCardIdList().isEmpty()) {

            log.debug("update child card's parent id to null : {}", card.getChildCardIdList());

            for (String childCardId : card.getChildCardIdList()) {

                Card childCard = cardRepository.findOne(childCardId);
                childCard.setParentCardId(null);
                cardRepository.save(childCard);

            }
        }

        if (card != null) {
            cardRepository.delete(id);
        }
    }

}
