package iingyeo.repository.predicate;

import com.mysema.query.types.Predicate;
import iingyeo.entity.QCard;

/**
 * Created by taemyung on 2015. 10. 10..
 */
public class CardPredicate {

    public static Predicate findLikeCardsByUserId(String userId) {

        QCard card = QCard.card;

        return card.likeUserIdSet.contains(userId);

    }

    public static Predicate findChildCardsByUserId(String userId) {

        QCard card = QCard.card;

        return card.userId.eq(userId).and(card.parentCardId.isNotNull());

    }

    public static Predicate findCardsByTagId(String tagId) {

        QCard card = QCard.card;

        return card.tagIdSet.contains(tagId);

    }

}
