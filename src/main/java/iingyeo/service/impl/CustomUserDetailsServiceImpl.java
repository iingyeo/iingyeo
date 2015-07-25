package iingyeo.service.impl;

import iingyeo.entity.User;
import iingyeo.model.IingyeoUserDetails;
import iingyeo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Taemyung on 2015-06-15.
 */
@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        log.debug("found user by username[{}] : {}", username, user);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User [%s] does not exist!", username));
        }
        return new IingyeoUserDetails(user);
    }

}
