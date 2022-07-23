package com.dreamsoftware.blockchaingateway.persistence.nosql.repository;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.CertificationAuthorityEventEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface CertificationAuthorityEventRepository extends MongoRepository<CertificationAuthorityEventEntity, ObjectId> {

}
