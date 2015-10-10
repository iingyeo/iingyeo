package iingyeo.service.impl;

import iingyeo.entity.Tag;
import iingyeo.model.TagListResponse;
import iingyeo.repository.TagRepository;
import iingyeo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by taemyung on 2015. 10. 9..
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag findByName(String name) {

        log.debug("find tag by name[{}]", name);

        Tag tag = tagRepository.findByName(name);

        if (tag == null) {
            // create new tag by name
            tag = new Tag(name);
            tagRepository.save(tag);
        }

        log.debug("find tag by name[{}] result : {}", name, tag);

        return tag;

    }

    @Override
    public TagListResponse getTags(int pageNum, int recordCount) {

        log.debug("get tags request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        Pageable pageRequest = new PageRequest(pageNum, recordCount, Sort.Direction.ASC, "name");

        Page<Tag> page = tagRepository.findAll(pageRequest);

        TagListResponse tagListResponse = new TagListResponse(page.getTotalPages(), page.getTotalElements(), pageNum, page.getContent());

        log.debug("get tags result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, tagListResponse);

        return tagListResponse;

    }

}
