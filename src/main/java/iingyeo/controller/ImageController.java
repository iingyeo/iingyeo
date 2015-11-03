package iingyeo.controller;

import com.mongodb.gridfs.GridFSDBFile;
import com.wordnik.swagger.annotations.ApiOperation;
import iingyeo.entity.Image;
import iingyeo.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Kang on 2015. 9. 2..
 */
@RestController
@RequestMapping(value = "/image")
@Slf4j
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "Add a Image", notes = "Add a Image")
    @RequestMapping(method = RequestMethod.POST)
    public Image addImage(@RequestBody MultipartFile file) {

        log.debug("add image request : {}", file);

        try {
            return imageService.addImage(file.getInputStream());
        } catch (Exception e) {

        }
        return null;
    }

    @ApiOperation(value = "Get a Image", notes = "Get a Image")
    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageId) {

        log.debug("get image request : {}", imageId);
        GridFSDBFile gridFSDBFile = imageService.getImage(imageId);

        return ResponseEntity.ok()
                .contentLength(gridFSDBFile.getLength())
                .contentType(MediaType.parseMediaType("image/jpg"))
                .body(new InputStreamResource(gridFSDBFile.getInputStream()));

    }

    @ApiOperation(value = "Get Image List", notes = "Get Image List")
    @RequestMapping(method = RequestMethod.GET)
    public void getImages() {

        log.debug("get image request : {}");
        List fils = imageService.listImage();

        log.debug("get user response ");

    }
}
