package iingyeo.model;

import iingyeo.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taemyung on 2015. 10. 10..
 */
@Data
public class TagListResponse implements Serializable {

    private Integer totalPage;
    private Long totalCount;
    private Integer pageNum;
    private List<TagResponse> tags;

    public TagListResponse() {
    }

    public TagListResponse(Integer totalPage, Long totalCount, Integer pageNum, List<Tag> tags) {

        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.pageNum = pageNum;

        this.tags = tags.stream().map(TagResponse::new).collect(Collectors.toList());

    }

}
