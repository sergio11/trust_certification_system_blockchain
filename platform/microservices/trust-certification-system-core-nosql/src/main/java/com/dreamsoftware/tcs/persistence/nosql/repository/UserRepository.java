package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;

import java.util.List;
import java.util.Optional;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> , UserRepositoryCustom {

    /**
     *
     * @param email
     * @return
     */
    Optional<UserEntity> findOneByEmail(final String email);

    /**
     *
     * @param key
     * @return
     */
    Optional<UserEntity> findOneByAuthProviderKey(final String key);

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
     * @param id
     * @return
     */
    Long countByIdAndCaIsNotNull(final ObjectId id);

    /**
     *
     * @param confirmationToken
     * @return
     */
    Optional<UserEntity> findOneByConfirmationToken(final String confirmationToken);

    /**
     *
     * @param state
     * @return
     */
    Long deleteByState(final UserStateEnum state);

    /**
     *
     * @param id
     * @return
     */
    List<UserEntity> findAllByCaId(final ObjectId id);

    /**
     *
     * @param id
     * @return
     */
    Long countById(final ObjectId id);
}
