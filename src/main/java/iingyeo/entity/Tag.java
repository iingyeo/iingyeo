package iingyeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by taemyung on 2015. 10. 9..
 */
@Data
@NoArgsConstructor
@Document
public class Tag implements Serializable {

    @Id
    private String id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }

}
