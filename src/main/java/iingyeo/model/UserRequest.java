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
public class UserRequest implements Serializable {

    private String username;
    private String password;

    public UserRequest() {
    }

    public User convertToUser() {

        User user = new User();

        IingyeoBeanUtils.copyNotNullProperties(this, user);

        return user;

    }
}
