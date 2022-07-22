package com.dreamsoftware.blockchaingateway.broadcasters;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.CertificationAuthorityEventRepository;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CertificationAuthorityBroadcaster {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityBroadcaster.class);

    private final CertificationAuthorityEventRepository certificationAuthorityEventRepository;
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    private final List<Disposable> disposableBag = new ArrayList<>();

    @PostConstruct
    public void onPostConstruct() {
        logger.debug("onPostConstruct CALLED!");
        observeCertificationAuthorityEvents();
    }

    @PreDestroy
    public void onPreDestroy() {
        logger.debug("onPreDestroy CALLED!");
        disposableBag.stream().filter((disposable) -> (!disposable.isDisposed())).forEachOrdered((disposable) -> {
            disposable.dispose();
        });
    }

    /**
     * Private Methods
     */
    private void observeCertificationAuthorityEvents() {
        try {
            disposableBag.add(certificationAuthorityBlockchainRepository.getEvents().subscribe(event -> {
                logger.debug("on CA Event!!");
            }));
        } catch (RepositoryException ex) {
            logger.debug("observeCertificationAuthorityEvents ex -> " + ex.getMessage());
        }
    }
}
