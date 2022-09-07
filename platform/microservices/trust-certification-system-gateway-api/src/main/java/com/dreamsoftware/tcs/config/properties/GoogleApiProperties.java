package com.dreamsoftware.tcs.config.properties;

import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
@Slf4j
public class GoogleApiProperties {

    /**
     * Client Secrets
     */
    @Value("classpath:client_secret.json")
    private Resource clientSecret;

    /**
     * Application Name
     */
    @Value("google.app.name")
    private String applicationName;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("GoogleApiProperties applicationName: " + applicationName);
    }

}
