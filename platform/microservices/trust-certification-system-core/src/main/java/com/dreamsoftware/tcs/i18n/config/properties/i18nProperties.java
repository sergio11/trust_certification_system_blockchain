package com.dreamsoftware.tcs.i18n.config.properties;

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
public class i18nProperties {

    @Value("${i18n.paramname}")
    private String paramName;

    @Value("${i18n.locale.supported}")
    private String localeSupported;

    @Value("${i18n.default.locale}")
    private String defaultLocale;

}
