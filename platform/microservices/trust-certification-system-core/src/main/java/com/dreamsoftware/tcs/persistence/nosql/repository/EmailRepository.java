package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
import org.bson.types.ObjectId;
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
     * @param id
     * @param type
     * @return
     */
    EmailEntity findByUserIdAndType(final ObjectId id, final EmailTypeEnum type);
}
