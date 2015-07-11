package iingyeo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by taemyung on 2015. 7. 11..
 */
@RestController
@Slf4j
public class AuthenticationController {

    @RequestMapping("/user")
    public Principal getLoggedInUser(Principal user) {

        log.debug("logged in user : [{}]", user.getName());

        return user;
    }
}
