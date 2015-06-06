package iingyeo.repository;

import iingyeo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Taemyung on 2015-06-06.
 */
public interface UserRepository extends MongoRepository<User, String> {

    public User findByEmailAddress(String emailAddress);

}
