package com.dreamsoftware.tcs.config.mail.properties;

import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
public class CryptoCompareProperties {

    private Logger logger = LoggerFactory.getLogger(CryptoCompareProperties.class);

    /**
     * Base Url
     */
    @Value("${cryptoCompare.baseUrl}")
    private String baseUrl;

    @PostConstruct
    public void onPostConstruct() {
        logger.debug("CryptoCompare BaseUrl: " + baseUrl);
    }
}
