package iingyeo.model;

import iingyeo.entity.Tag;
import iingyeo.util.IingyeoBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by taemyung on 2015. 10. 10..
 */
@Data
@NoArgsConstructor
public class TagResponse implements Serializable {

    private String id;
    private String name;

    public TagResponse(Tag tag) {
        IingyeoBeanUtils.copyNotNullProperties(tag, this);
    }
}
