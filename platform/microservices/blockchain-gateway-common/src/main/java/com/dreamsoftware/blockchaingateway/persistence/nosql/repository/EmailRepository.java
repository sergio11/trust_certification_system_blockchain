package com.dreamsoftware.blockchaingateway.persistence.nosql.repository;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailTypeEnum;
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
     * @param email
     * @param type
     * @return
     */
    EmailEntity findByUserEmailAndType(final String email, final EmailTypeEnum type);
}
