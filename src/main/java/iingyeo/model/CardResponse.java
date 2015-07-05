package iingyeo.model;

import iingyeo.entity.Card;
import iingyeo.util.IingyeoBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kang on 2015. 7. 1..
 */
@Data
@NoArgsConstructor
public class CardResponse implements Serializable {

    private String id;
    private String userId;

    private String text;
    private String backgroundUrl;


    private Date created;
    private Date updated;

    public CardResponse(Card card) {

        IingyeoBeanUtils.copyNotNullProperties(card, this);

    }
}
