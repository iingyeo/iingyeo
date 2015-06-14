package iingyeo.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import iingyeo.entity.User;
import iingyeo.model.UserListResponse;
import iingyeo.model.UserRequest;
import iingyeo.model.UserResponse;
import iingyeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Taemyung on 2015-06-06.
 */
@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Add an user", notes = "Add an user")
    @RequestMapping(method = RequestMethod.POST)
    public UserResponse addUser(@RequestBody UserRequest userRequest) {

        log.debug("add user request : {}", userRequest);

        User user = userService.addUser(userRequest.convertToUser());

        UserResponse userResponse = new UserResponse(user);

        log.debug("add user response : {}", userResponse);

        return userResponse;

    }

    @ApiOperation(value = "Get an user by Id", notes = "Get an user by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public UserResponse getUser(@PathVariable String id) {

        log.debug("get user request for id[{}]", id);

        User user = userService.getUser(id);

        UserResponse userResponse = new UserResponse(user);

        log.debug("get user response for id[{}] : {}", id, userResponse);

        return userResponse;

    }

    @ApiOperation(value = "Get users", notes = "Get users by pageNum, recordCount")
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("#oauth2.hasScope('read')")
    public UserListResponse getUsers(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int recordCount) {

        log.debug("get users request for pageNum[{}], recordCount[{}]", pageNum, recordCount);

        UserListResponse userListResponse = userService.getUsers(pageNum, recordCount);

        log.debug("get users result for pageNum[{}], recordCount[{}] : {}", pageNum, recordCount, userListResponse);

        return userListResponse;

    }

    @ApiOperation(value = "Update an user", notes = "Update an user")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("#oauth2.hasScope('write')")
    public UserResponse updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {

        log.debug("update user request for id[{}] : {}", id, userRequest);

        User user = userRequest.convertToUser();

        user.setId(id);

        User updatedUser = userService.updateUser(user);

        UserResponse userResponse = new UserResponse(updatedUser);

        log.debug("update user result for id[{}] : {}", id, userResponse);

        return userResponse;

    }

    @ApiOperation(value = "Delete an user by Id", notes = "Delete an user by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("#oauth2.hasScope('write')")
    public void deleteUser(@PathVariable String id) {

        log.debug("delete user request for id[{}]", id);

        userService.deleteUser(id);
    }

}
