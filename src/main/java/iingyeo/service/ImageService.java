package iingyeo.service;


import com.mongodb.gridfs.GridFSDBFile;
import iingyeo.entity.Image;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Kang on 2015. 9. 21..
 */
public interface ImageService {

    Image addImage(InputStream inputStream);

    GridFSDBFile getImage(String imageId);

    List listImage();
}
