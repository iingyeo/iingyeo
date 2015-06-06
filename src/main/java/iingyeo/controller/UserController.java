package iingyeo.controller;

import iingyeo.entity.User;
import iingyeo.model.UserListResponse;
import iingyeo.model.UserRequest;
import iingyeo.model.UserResponse;
import iingyeo.repository.UserRepository;
import iingyeo.util.IingyeoBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Taemyung on 2015-06-06.
 */
@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public UserResponse addUser(@RequestBody UserRequest userRequest) {

        log.debug("add user request : {}", userRequest);

        User user = userRepository.save(userRequest.convertToUser());

        UserResponse userResponse = new UserResponse(user);

        log.debug("add user response : {}", userResponse);

        return userResponse;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserResponse getUser(@PathVariable String id) {

        log.debug("get user request for id[{}]", id);

        UserResponse userResponse = new UserResponse(userRepository.findOne(id));

        log.debug("get user response for id[{}]", id, userResponse);

        return userResponse;

    }

    @RequestMapping(method = RequestMethod.GET)
    public UserListResponse getUsers(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int recordCount) {

        log.debug("get users request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        Pageable pageRequest = new PageRequest(pageNum, recordCount);

        Page<User> page = userRepository.findAll(pageRequest);

        UserListResponse userListResponse = new UserListResponse(page.getTotalPages(), page.getTotalElements(), pageNum, page.getContent());

        log.debug("get users result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, userListResponse);

        return userListResponse;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserResponse updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {

        log.debug("update user request for id[{}] : {}", id, userRequest);

        User targetUser = userRepository.findOne(id);

        IingyeoBeanUtils.copyNotNullProperties(userRequest.convertToUser(), targetUser);

        log.debug("target user : {}", targetUser);

        User updatedUser = userRepository.save(targetUser);

        UserResponse userResponse = new UserResponse(updatedUser);

        log.debug("update user result for id[{}] : {}", id, userResponse);

        return userResponse;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) {

        log.debug("delete user request for id[{}]", id);

        userRepository.delete(id);

    }

}
