package iingyeo.service.impl;

import iingyeo.entity.User;
import iingyeo.model.IingyeoUserDetails;
import iingyeo.model.UserListResponse;
import iingyeo.model.UserResponse;
import iingyeo.repository.UserRepository;
import iingyeo.service.UserService;
import iingyeo.util.IingyeoBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

/**
 * Created by rainhelper on 2015. 6. 13..
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {

        log.debug("add user request : {}", user);

        String hashedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

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
        targetUser.setUpdated(new Date());

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

    @Override
    public User getLoggedInUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.debug("get logged in user request for auth[{}]", auth.getName());

        IingyeoUserDetails userDetails = (IingyeoUserDetails) auth.getPrincipal();

        User loggedInUser = userDetails.getUser();

        log.debug("get logged in user response for auth[{}] : {}", auth.getName(), loggedInUser);

        return loggedInUser;

    }
}
