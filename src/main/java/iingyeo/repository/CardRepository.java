package iingyeo.repository;

import iingyeo.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Collection;

/**
 * Created by Kang on 2015. 7. 1..
 */
public interface CardRepository extends MongoRepository<Card, String>, QueryDslPredicateExecutor<Card> {

}
