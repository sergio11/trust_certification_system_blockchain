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
    Page<NotificationEntity> findByUserIdOrderByCreateAtDesc(final ObjectId id, final Pageable pageable);

    /**
     *
     * @param from
     * @param id
     * @param pageable
     * @return
     */
    Page<NotificationEntity> findByCreateAtGreaterThanEqualAndUserIdOrderByCreateAtDesc(final Date from, final ObjectId id, final Pageable pageable);

}