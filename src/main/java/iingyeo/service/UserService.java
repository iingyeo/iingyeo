package iingyeo.service;

import iingyeo.entity.User;
import iingyeo.model.UserListResponse;

import java.security.Principal;

/**
 * Created by rainhelper on 2015. 6. 13..
 */
public interface UserService {

    User addUser(User user);

    User getUser(String id);

    UserListResponse getUsers(int pageNum, int recordCount);

    User updateUser(User user);

    void deleteUser(String id);

    User getLoggedInUser();

}
