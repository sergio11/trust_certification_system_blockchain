package com.dreamsoftware.blockchaingateway.persistence.nosql.repository;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EmailTypeEnum;
import java.util.Optional;
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
