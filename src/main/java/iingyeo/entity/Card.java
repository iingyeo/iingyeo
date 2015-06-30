package iingyeo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kang on 2015. 6. 30..
 */

@Data
@Document
public class Card implements Serializable{

    @Id
    private String id;

    private Long userId;

    private String text;
    private String backgroundUrl;

    private Date created;
    private Date updated;
}
