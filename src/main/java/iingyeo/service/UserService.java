package iingyeo.service;

import iingyeo.entity.User;
import iingyeo.model.UserListResponse;

/**
 * Created by rainhelper on 2015. 6. 13..
 */
public interface UserService {

    public User addUser(User user);

    public User getUser(String id);

    UserListResponse getUsers(int pageNum, int recordCount);

    public User updateUser(User user);

    public void deleteUser(String id);

}
