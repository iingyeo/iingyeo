package iingyeo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kang on 2015. 10. 5..
 */

@Data
@Document
public class Image implements Serializable{


    @Id
    private String id;

    private String fontColor;

    private int height;
    private int width;

    private String storeType;

    private String path;

    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date updated;

}
