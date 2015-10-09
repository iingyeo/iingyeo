package iingyeo.repository;

import iingyeo.entity.Card;
import iingyeo.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by taemyung on 2015. 10. 9..
 */
public interface TagRepository extends MongoRepository<Tag, String>, QueryDslPredicateExecutor<Tag> {

    Tag findByName(String name);

}
