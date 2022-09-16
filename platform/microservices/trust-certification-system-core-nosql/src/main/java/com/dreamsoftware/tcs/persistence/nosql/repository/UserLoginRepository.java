package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserLoginEntity;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface UserLoginRepository extends MongoRepository<UserLoginEntity, ObjectId> {

    /**
     *
     * @param userId
     * @return
     */
    Optional<UserLoginEntity> findFirstByUserIdOrderByCreatedAtDesc(final ObjectId userId);

    /**
     *
     * @param pageable
     * @param userId
     * @return
     */
    Page<UserLoginEntity> findByUserId(final Pageable pageable, final ObjectId userId);

    /**
     *
     * @param userId
     */
    void deleteAllByUserId(final ObjectId userId);

}
