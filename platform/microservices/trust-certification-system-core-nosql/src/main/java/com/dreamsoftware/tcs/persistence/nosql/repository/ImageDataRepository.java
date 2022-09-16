package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.ImageDataEntity;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface ImageDataRepository extends MongoRepository<ImageDataEntity, ObjectId> {

    /**
     *
     * @param name
     * @return
     */
    Optional<ImageDataEntity> findByName(final String name);

}
