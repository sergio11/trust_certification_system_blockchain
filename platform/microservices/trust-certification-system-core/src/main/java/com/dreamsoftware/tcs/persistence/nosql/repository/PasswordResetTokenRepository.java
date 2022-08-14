package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.PasswordResetTokenEntity;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetTokenEntity, ObjectId> {

    /**
     * Find By Token
     *
     * @param token
     * @return
     */
    PasswordResetTokenEntity findByToken(String token);

    /**
     *
     * @param email
     * @return
     */
    PasswordResetTokenEntity findByUserEmail(String email);

    /**
     * Delete By Expiry Date Before
     *
     * @param date
     */
    void deleteByExpiryDateBefore(Date date);
}
