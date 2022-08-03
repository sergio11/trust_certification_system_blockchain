package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface CreatedOrderRepository extends MongoRepository<CreatedOrderEntity, ObjectId>, CertificateIssuanceRequestRepositoryCustom {

}
