package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    /**
     *
     * @param email
     * @return
     */
    Optional<UserEntity> findOneByEmail(String email);

    /**
     *
     * @param walletHash
     * @return
     */
    Optional<UserEntity> findOneByWalletHash(final String walletHash);

    /**
     *
     * @param email
     * @return
     */
    Long countByEmail(final String email);

    /**
     *
     * @param confirmationToken
     * @return
     */
    Optional<UserEntity> findOneByConfirmationToken(String confirmationToken);

    /**
     *
     * @param state
     * @return
     */
    Long deleteByState(final UserStateEnum state);
}
