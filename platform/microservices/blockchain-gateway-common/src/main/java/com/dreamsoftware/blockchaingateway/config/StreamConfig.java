package com.dreamsoftware.blockchaingateway.config;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.service.IEventPublisher;
import com.dreamsoftware.blockchaingateway.service.impl.EventPublisherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
public class StreamConfig {

    /**
     * Provider Event Publisher
     *
     * @return
     */
    @Bean
    public IEventPublisher<CertificationAuthorityInitialFundsRequestEvent> provideEventPublisher() {
        return new EventPublisherImpl<>();
    }

}
