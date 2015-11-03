package iingyeo.repository;

import iingyeo.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Kang on 2015. 11. 4..
 */
public interface ImageRepository extends MongoRepository<Image, String>, QueryDslPredicateExecutor<Image> {

}
