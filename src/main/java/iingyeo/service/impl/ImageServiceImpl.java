package iingyeo.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import iingyeo.entity.Image;
import iingyeo.repository.ImageRepository;
import iingyeo.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    private ImageRepository imageRepository;

    @Autowired
    private GridFsOperations gridFsOperation;

    @Override
    public Image addImage(InputStream inputStream) {

        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(inputStream);

        } catch (Exception e) {

        }

        // Make Image Model
        String imageName = UUID.randomUUID().toString();

        Image image = new Image();
        image.setPath("/image/" + imageName);
        image.setHeight(bufferedImage.getHeight());
        image.setWidth(bufferedImage.getWidth());

        imageRepository.save(image);

        // Make DBObject
        DBObject metaData = new BasicDBObject();
        metaData.put("ImageId", imageName);

        //Store Image to MongoDB
        try {

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            GridFSFile file = gridFsOperation.store(is, imageName, metaData);

            log.debug("imageName Id {}", file.getId().toString());
        } catch (Exception e) {

        } finally {

        }
        return image;
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
