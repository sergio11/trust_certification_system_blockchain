package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *
 * @author ssanchez
 */
@Repository
public interface AuthorityRepository extends MongoRepository<AuthorityEntity, ObjectId> {

    /**
     *
     * @param type
     * @return
     */
    Optional<AuthorityEntity> findOneByType(AuthorityEnum type);
}
