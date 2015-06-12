package iingyeo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Taemyung on 2015-06-06.
 */
@Data
@Document
public class User implements Serializable {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private Date birthDate;

    public User() {
    }

    public User(String firstName, String lastName, String emailAddress, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

}
