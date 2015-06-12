package iingyeo.repository;

import iingyeo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Taemyung on 2015-06-06.
 */
public interface UserRepository extends MongoRepository<User, String>, QueryDslPredicateExecutor<User> {

    public User findByEmailAddress(String emailAddress);

}
