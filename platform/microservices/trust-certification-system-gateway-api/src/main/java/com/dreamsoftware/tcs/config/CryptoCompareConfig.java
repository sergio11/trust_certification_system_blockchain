package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.config.properties.CryptoCompareProperties;
import com.dreamsoftware.tcs.web.interceptors.HeaderRequestInterceptor;
import com.dreamsoftware.tcs.web.interceptors.LoggingRequestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ssanchez
 */
@Configuration
public class CryptoCompareConfig {

    /**
     * Provide Loggin Request Interceptor
     *
     * @return
     */
    @Bean(name = "loggingRequestInterceptor")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ClientHttpRequestInterceptor provideLoggingRequestInterceptor() {
        return new LoggingRequestInterceptor();
    }

    /**
     * Provide Authorization Interceptor
     *
     * @param cryptoCompareProperties
     * @return
     */
    @Bean(name = "authorizationInterceptor")
    @Order(1)
    public ClientHttpRequestInterceptor provideAuthorizationInterceptor(final CryptoCompareProperties cryptoCompareProperties) {
        return new HeaderRequestInterceptor("Authorization", String.format("key=%s", cryptoCompareProperties.getApiKey()));
    }

    /**
     * Provide Client Http Request Factory
     *
     * @return
     */
    @Bean
    public ClientHttpRequestFactory provideClientHttpRequestFactory() {
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(5000);
        return clientHttpRequestFactory;
    }

    /**
     *
     * @param objectMapper
     * @param clientHttpRequestFactory
     * @param loggingRequestInterceptor
     * @param authorizationInterceptor
     * @param messageConverter
     * @return
     */
    @Bean(name = "cryptoCompareRestTemplate")
    public RestTemplate cryptoCompareRestTemplate(
            final ObjectMapper objectMapper,
            final ClientHttpRequestFactory clientHttpRequestFactory,
            final @Qualifier("loggingRequestInterceptor") ClientHttpRequestInterceptor loggingRequestInterceptor,
            final @Qualifier("authorizationInterceptor") ClientHttpRequestInterceptor authorizationInterceptor,
            final MappingJackson2HttpMessageConverter messageConverter) {
        final RestTemplate rest = new RestTemplate(Collections.singletonList(messageConverter));
        rest.setRequestFactory(clientHttpRequestFactory);
        rest.setInterceptors(Lists.newArrayList(loggingRequestInterceptor, authorizationInterceptor));
        return rest;
    }
}
