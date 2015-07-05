package iingyeo.model;

import iingyeo.entity.Card;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rainhelper on 2015. 7. 5..
 */
@Data
public class CardListResponse implements Serializable {

    private Integer totalPage;
    private Long totalCount;
    private Integer pageNum;
    private List<CardResponse> cards;

    public CardListResponse() {
    }

    public CardListResponse(Integer totalPage, Long totalCount, Integer pageNum, List<Card> cards) {

        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageNum = pageNum;

        this.cards = cards.stream().map(CardResponse::new).collect(Collectors.toList());

    }
}
