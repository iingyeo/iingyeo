package iingyeo.service;

import iingyeo.entity.Tag;
import iingyeo.model.TagListResponse;

/**
 * Created by taemyung on 2015. 10. 9..
 */
public interface TagService {

    Tag findByName(String name);

    TagListResponse getTags(int pageNum, int recordCount);

}
