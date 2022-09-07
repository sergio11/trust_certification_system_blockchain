package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderEntity;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface AuthenticationProviderRepository extends MongoRepository<AuthenticationProviderEntity, ObjectId>, AuthenticationProviderRepositoryCustom {

    /**
     * Find Auth Provider by key
     *
     * @param key
     * @return
     */
    Optional<AuthenticationProviderEntity> findOneByKey(final String key);
}
