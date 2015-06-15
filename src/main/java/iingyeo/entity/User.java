package iingyeo.entity;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Taemyung on 2015-06-06.
 */
@Data
@Document
public class User implements Serializable {

    private final static String USER_ROLE = "USER_ROLE";

    @Id
    private String id;

    private String username;
    private String password;

    private Set<Role> roles;

    private Date created;
    private Date updated;

    public User() {
        this.roles = Sets.newHashSet(new Role(USER_ROLE));

        this.created = new Date();
        this.updated = this.created;
    }

    public User(String username, String password) {
        this();

        this.username = username;
        this.password = password;
    }

    @Data
    public static class Role implements GrantedAuthority {

        private String authority;

        public Role() {
        }

        public Role(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }

    }

}
