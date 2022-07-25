package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.AbstractBlockchainEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import io.reactivex.Flowable;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public interface IBlockchainEventRepository<T extends AbstractBlockchainEventEntity> {

    /**
     *
     * @return @throws RepositoryException
     */
    Flowable<T> getEvents() throws RepositoryException;

}
