package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationEntity;
import java.util.Date;
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
public interface NotificationRepository extends MongoRepository<NotificationEntity, ObjectId> {

    /**
     *
     * @param id
     * @param pageable
     * @return
     */
    Page<NotificationEntity> findByUserIdOrderByCreatedAtDesc(final ObjectId id, final Pageable pageable);

    /**
     *
     * @param from
     * @param id
     * @param pageable
     * @return
     */
    Page<NotificationEntity> findByCreatedAtGreaterThanEqualAndUserIdOrderByCreatedAtDesc(final Date from, final ObjectId id, final Pageable pageable);

    /**
     *
     * @param id
     * @param userId
     * @return
     */
    Long countByIdAndUserId(final ObjectId id, final ObjectId userId);

    Long countById(final ObjectId id);

}
