package iingyeo.model;

import iingyeo.entity.User;
import iingyeo.util.IingyeoBeanUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Taemyung on 2015-06-06.
 */
@Data
public class UserResponse implements Serializable {

    private String id;
    private String username;
    private Date created;
    private Date updated;

    public UserResponse() {
    }

    public UserResponse(User user) {

        IingyeoBeanUtils.copyNotNullProperties(user, this);

    }
}
