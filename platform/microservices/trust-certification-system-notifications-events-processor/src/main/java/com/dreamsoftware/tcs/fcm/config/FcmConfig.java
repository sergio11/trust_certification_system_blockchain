package com.dreamsoftware.tcs.fcm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FcmConfig {

    /**
     *
     * @param objectMapper
     * @param interceptors
     * @param converter
     * @param responseErrorHandler
     * @return
     */
    @Bean("fcmRestTemplate")
    public RestTemplate fcmRestTemplate(ObjectMapper objectMapper, List<ClientHttpRequestInterceptor> interceptors,
            MappingJackson2HttpMessageConverter converter, DefaultResponseErrorHandler responseErrorHandler) {
        log.debug("Total interceptors: " + interceptors.size());
        RestTemplate restTemplate = new RestTemplate(Collections.singletonList(converter));
        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(responseErrorHandler);
        return restTemplate;
    }

}
