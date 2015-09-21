package iingyeo.model;

import iingyeo.entity.Card;
import iingyeo.util.IingyeoBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private String parentCardId;
    private List<String> childCardIdList;

    private Date created;
    private Date updated;

    public CardResponse(Card card) {

        IingyeoBeanUtils.copyNotNullProperties(card, this);

    }
}
