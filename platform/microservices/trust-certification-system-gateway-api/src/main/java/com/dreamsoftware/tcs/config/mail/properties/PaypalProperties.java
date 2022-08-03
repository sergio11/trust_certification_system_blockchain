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
public class PaypalProperties {

    private Logger logger = LoggerFactory.getLogger(PaypalProperties.class);

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
        logger.debug("Paypal Client Id: " + clientId);
        logger.debug("Paypal Client Secret: " + clientSecret);
    }
}
