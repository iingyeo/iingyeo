package iingyeo.service.impl;

import iingyeo.entity.Tag;
import iingyeo.repository.TagRepository;
import iingyeo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

}
