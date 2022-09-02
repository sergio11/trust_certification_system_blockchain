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
public class PaypalProperties {

    /**
     * Client Id
     */
    @Value("${paypal.client.id}")
    private String clientId;

    /**
     * Client Secret
     */
    @Value("${paypal.client.secret}")
    private String clientSecret;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("Paypal Client Id: " + clientId);
        log.debug("Paypal Client Secret: " + clientSecret);
    }
}
