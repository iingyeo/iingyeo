package iingyeo.model;

import iingyeo.entity.Card;
import iingyeo.util.IingyeoBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Kang on 2015. 7. 1..
 */
@Data
@NoArgsConstructor
public class CardRequest implements Serializable {

    private String text;
    private String backgroundUrl;

    public Card convertToCard(String userId) {

        Card card = new Card();

        IingyeoBeanUtils.copyNotNullProperties(this, card);

        card.setUserId(userId);

        return card;

    }
}
