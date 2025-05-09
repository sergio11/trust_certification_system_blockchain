package com.dreamsoftware.tcs.observer;

import com.dreamsoftware.tcs.persistence.bc.repository.IBlockchainEventRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.AbstractBlockchainEventEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.tcs.persistence.nosql.entity.BlockchainEventLogEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.BlockchainEventLogRepository;
import io.reactivex.disposables.Disposable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssanchez
 * @param <T>
 */
@RequiredArgsConstructor
@Slf4j
public abstract class SupportEventObserver<T extends AbstractBlockchainEventEntity> {

    private final BlockchainEventLogRepository blockchainEventLogRepository;
    private final IBlockchainEventRepository<T> blockchainEventRepository;
    private Disposable disposable = null;

    protected void startObserverEvents() {
        log.debug("startObserverEvents CALLED!");
        try {
            disposable = blockchainEventRepository.getEvents().subscribe(event -> {
                log.debug("on new Event!!");
                final BlockchainEventLogEntity logEntity = mapToBlockchainEventLogEntity(event);
                blockchainEventLogRepository.save(logEntity);
            });
        } catch (RepositoryException ex) {
            log.debug("SupportEventBroadcaster ex -> " + ex.getMessage());
        }
    }

    protected void stopObserverEvents() {
        log.debug("stopObserverEvents CALLED!");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     *
     * @param event
     * @return
     */
    abstract AbstractEventPayload mapToPayload(T event);

    /**
     *
     * @param event
     * @return
     */
    private BlockchainEventLogEntity mapToBlockchainEventLogEntity(T event) {
        return BlockchainEventLogEntity
                .builder()
                .address(event.getAddress())
                .blockHash(event.getBlockHash())
                .blockNumber(event.getBlockNumber())
                .blockNumberRaw(event.getBlockNumberRaw())
                .transactionHash(event.getTransactionHash())
                .transactionIndex(event.getTransactionIndex())
                .transactionIndexRaw(event.getTransactionIndexRaw())
                .logIndex(event.getLogIndex())
                .logIndexRaw(event.getLogIndexRaw())
                .data(event.getData())
                .payload(mapToPayload(event))
                .build();
    }

    @PostConstruct
    public void onPostConstruct() {
        startObserverEvents();
    }

    @PreDestroy
    public void onPreDestroy() {
        stopObserverEvents();
    }
}
