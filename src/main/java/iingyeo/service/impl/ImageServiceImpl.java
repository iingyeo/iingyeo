package iingyeo.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import iingyeo.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Kang on 2015. 10. 19..
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private GridFsOperations gridFsOperation;

    @Override
    public String addImage(InputStream inputStream) {

        String imageName = UUID.randomUUID().toString();

        DBObject metaData = new BasicDBObject();
        metaData.put("ImageId", imageName);


        GridFSFile file = gridFsOperation.store(inputStream, imageName, metaData);

        log.debug("imageName Id {}", file.getId().toString());

        return imageName;
    }

    @Override
    public GridFSDBFile getImage(String imageId) {
        GridFSDBFile file = gridFsOperation.findOne(new Query().addCriteria(Criteria.where("filename").is(imageId)));

        return file;
    }

    @Override
    public List listImage() {

        return gridFsOperation.find(null);
    }

}
