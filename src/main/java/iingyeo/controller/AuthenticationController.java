package iingyeo.controller;

import iingyeo.entity.User;
import iingyeo.model.IingyeoUserDetails;
import iingyeo.model.UserResponse;
import iingyeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by taemyung on 2015. 7. 11..
 */
@RestController
@Slf4j
public class AuthenticationController {

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Autowired
    private DefaultTokenServices tokenService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserResponse getLoggedInUser(Principal principal) {

        OAuth2Authentication auth = (OAuth2Authentication) principal;

        IingyeoUserDetails userDetails = (IingyeoUserDetails) auth.getPrincipal();

        UserResponse userResponse = new UserResponse(userDetails.getUser());

        log.debug("logged in user : [{}]", userResponse);

        return userResponse;

    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = tokenExtractor.extract(request);

        log.debug("log out by access token[{}]", auth.getPrincipal());

        String accessToken = auth.getPrincipal().toString();

        return tokenService.revokeToken(accessToken);

    }
}
