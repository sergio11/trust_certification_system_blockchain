package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.config.properties.PaypalProperties;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
@EnableEncryptableProperties
public class PayPalConfig {

    /**
     *
     * @param paypalProperties
     * @return
     */
    @Bean(name = "payPalHttpClient")
    public PayPalHttpClient getPayPalHttpClient(final PaypalProperties paypalProperties) {
        return new PayPalHttpClient(new PayPalEnvironment.Sandbox(paypalProperties.getClientId(), paypalProperties.getClientSecret()));
    }
}
