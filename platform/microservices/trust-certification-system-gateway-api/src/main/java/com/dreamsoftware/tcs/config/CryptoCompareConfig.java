package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.config.mail.properties.PaypalProperties;
import com.dreamsoftware.tcs.web.interceptors.HeaderRequestInterceptor;
import com.dreamsoftware.tcs.web.interceptors.LoggingRequestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ssanchez
 */
@Configuration
@EnableEncryptableProperties
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
     * @return
     */
    @Bean(name = "authorizationInterceptor")
    @Order(1)
    public ClientHttpRequestInterceptor provideAuthorizationInterceptor() {
        return new HeaderRequestInterceptor("Authorization", String.format("key=%s", ""));
    }

    /**
     * Provide Client Http Request Factory
     *
     * @return
     */
    @Bean
    public ClientHttpRequestFactory provideClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    /**
     *
     * @param objectMapper
     * @param clientHttpRequestFactory
     * @param loggingRequestInterceptor
     * @param authorizationInterceptor
     * @return
     */
    @Bean("cryptoCompareRestTemplate")
    public RestTemplate cryptoCompareRestTemplate(
            final ObjectMapper objectMapper,
            final ClientHttpRequestFactory clientHttpRequestFactory,
            final @Qualifier("loggingRequestInterceptor") ClientHttpRequestInterceptor loggingRequestInterceptor,
            final @Qualifier("authorizationInterceptor") ClientHttpRequestInterceptor authorizationInterceptor) {
        final RestTemplate rest = new RestTemplate(Collections.singletonList(new FormHttpMessageConverter()));
        rest.setRequestFactory(clientHttpRequestFactory);
        rest.setInterceptors(Lists.newArrayList(loggingRequestInterceptor, authorizationInterceptor));
        return rest;
    }
}