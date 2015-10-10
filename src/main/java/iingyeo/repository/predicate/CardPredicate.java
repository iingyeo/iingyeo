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
}
