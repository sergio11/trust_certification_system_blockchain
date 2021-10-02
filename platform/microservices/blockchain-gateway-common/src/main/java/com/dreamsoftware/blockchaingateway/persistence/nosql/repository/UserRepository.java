package com.dreamsoftware.blockchaingateway.persistence.nosql.repository;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserStateEnum;
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
     * @param email
     * @param userName
     * @return
     */
    Optional<UserEntity> findOneByEmailOrUserName(String email, String userName);

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
