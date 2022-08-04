package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface CreatedOrderRepository extends MongoRepository<CreatedOrderEntity, ObjectId>, CertificateIssuanceRequestRepositoryCustom {

    /**
     * Find By External Order Id
     *
     * @param id
     * @return
     */
    Optional<CreatedOrderEntity> findByExternalOrderId(final String id);

    /**
     * Find By External Order Id And Security Token
     *
     * @param id
     * @param securityToken
     * @return
     */
    Optional<CreatedOrderEntity> findByExternalOrderIdAndSecurityToken(final String id, final String securityToken);

}
