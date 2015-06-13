package iingyeo.service.impl;

import iingyeo.entity.User;
import iingyeo.model.UserListResponse;
import iingyeo.repository.UserRepository;
import iingyeo.service.UserService;
import iingyeo.util.IingyeoBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rainhelper on 2015. 6. 13..
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {

        log.debug("add user request : {}", user);

        userRepository.save(user);

        log.debug("add user response : {}", user);

        return user;
    }

    @Override
    public User getUser(String id) {

        log.debug("get user request for id[{}]", id);

        User user = userRepository.findOne(id);

        log.debug("get user response for id[{}] : {}", id, user);

        return user;
    }

    @Override
    public UserListResponse getUsers(int pageNum, int recordCount) {

        log.debug("get users request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        Pageable pageRequest = new PageRequest(pageNum, recordCount);

        Page<User> page = userRepository.findAll(pageRequest);

        UserListResponse userListResponse = new UserListResponse(page.getTotalPages(), page.getTotalElements(), pageNum, page.getContent());

        log.debug("get users result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, userListResponse);

        return userListResponse;
    }

    @Override
    public User updateUser(User user) {

        log.debug("update user request : {}", user);

        User targetUser = userRepository.findOne(user.getId());

        IingyeoBeanUtils.copyNotNullProperties(user, targetUser);

        User updatedUser = userRepository.save(targetUser);

        log.debug("update user response : {}", targetUser);

        return updatedUser;
    }

    @Override
    public void deleteUser(String id) {

        log.debug("delete user for id[{}]", id);

        User user = userRepository.findOne(id);

        if (user != null) {
            userRepository.delete(id);
        }
    }
}
