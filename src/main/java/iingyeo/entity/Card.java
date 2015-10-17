package iingyeo.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Kang on 2015. 6. 30..
 */

@Data
@Document
public class Card implements Serializable {

    @Id
    private String id;

    private String userId;

    private String text;
    private String backgroundUrl;

    private String parentCardId;
    private List<String> childCardIdList = new ArrayList<>();
    private int childCardCount;

    private Set<String> likeUserIdSet = new HashSet<>();
    private int likeUserCount;

    private Set<String> tagIdSet = new HashSet<>();

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date updated;

    public void addChildCardId(String childCardId) {
        this.childCardIdList.add(childCardId);
        this.childCardCount = childCardIdList.size();
    }

    public boolean like(String likeUserId) {
        boolean result = this.likeUserIdSet.add(likeUserId);
        this.likeUserCount = likeUserIdSet.size();

        return result;
    }

    public void addTagId(String tagId) {
        this.tagIdSet.add(tagId);
    }

    public Set<String> filterTags() {
        Set<String> tags = new HashSet<>();

        for (String word : StringUtils.split(text)) {
            if (word.startsWith("#") && word.length() > 1) {
                tags.add(word.substring(1));
            }
        }

        return tags;
    }

}
