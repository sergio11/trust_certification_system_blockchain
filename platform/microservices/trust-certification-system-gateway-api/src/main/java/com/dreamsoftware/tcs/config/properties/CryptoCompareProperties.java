package com.dreamsoftware.tcs.config.properties;

import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
@Slf4j
public class CryptoCompareProperties {

    /**
     * Base Url
     */
    @Value("${cryptoCompare.baseUrl}")
    private String baseUrl;

    /**
     * Apy Key
     */
    @Value("${cryptoCompare.apiKey}")
    private String apiKey;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("CryptoCompare BaseUrl: " + baseUrl);
        log.debug("CryptoCompare apiKey: " + apiKey);
    }
}
