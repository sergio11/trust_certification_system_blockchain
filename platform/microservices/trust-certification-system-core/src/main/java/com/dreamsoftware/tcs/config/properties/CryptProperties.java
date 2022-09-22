package com.dreamsoftware.tcs.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
public class CryptProperties {

    /**
     * Crypt Password
     */
    @Value("${crypt.password}")
    private String password;

    /**
     * Crypt Salt
     */
    @Value("${crypt.salt}")
    private String salt;

    /**
     * Crypt Separator
     */
    @Value("${crypt.separator}")
    private String separator;

}
