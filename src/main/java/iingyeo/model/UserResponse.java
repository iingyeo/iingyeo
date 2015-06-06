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
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Date birthDate;

    public UserResponse() {
    }

    public UserResponse(User user) {

        IingyeoBeanUtils.copyNotNullProperties(user, this);

    }
}
