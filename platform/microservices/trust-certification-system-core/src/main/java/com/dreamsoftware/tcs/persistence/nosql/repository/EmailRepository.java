package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.EmailEntity;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface EmailRepository extends MongoRepository<EmailEntity, ObjectId> {

    /**
     *
     * @param pageable
     * @return
     */
    List<EmailEntity> findAllOrderByLastChanceAsc(final Pageable pageable);

}
