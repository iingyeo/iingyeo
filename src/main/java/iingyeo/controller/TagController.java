package iingyeo.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import iingyeo.model.TagListResponse;
import iingyeo.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by taemyung on 2015. 10. 10..
 */
@RestController
@RequestMapping(value = "/tags")
@Slf4j
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "Get tags", notes = "Get tags by pageNum, recordCount")
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public TagListResponse getTags(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "100") int recordCount) {

        log.debug("get tags request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        TagListResponse tagListResponse = tagService.getTags(pageNum, recordCount);

        log.debug("get tags result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, tagListResponse);

        return tagListResponse;

    }

}
