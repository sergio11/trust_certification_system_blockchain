package com.dreamsoftware.blockchaingateway.config;

import com.dreamsoftware.blockchaingateway.i18n.resolver.SmartLocaleResolver;
import com.dreamsoftware.blockchaingateway.i18n.service.I18NService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 *
 * @author ssanchez
 */
@Configuration
public class MiscConfig {

    /**
     * i18n param name
     */
    @Value("${i18n.paramname}")
    private String paramName;

    /**
     * Provide Locale Change Interceptor
     *
     * @return LocaleChangeInterceptor
     */
    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor provideLocaleChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(paramName);
        return localeChangeInterceptor;
    }

    /**
     * Provide Locale Resolver The LocaleResolver interface has implementations
     * that determine the current locale based on the session, cookies, the
     * Accept-Language header, or a fixed value.
     *
     * @param i18nService
     * @return LocaleResolver
     */
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver(final I18NService i18nService) {
        return new SmartLocaleResolver(i18nService);
    }

    /**
     * Provide Pretty time as suitable date format utility
     *
     * @return PrettyTime
     */
    @Bean(name = "prettyTime")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrettyTime providePrettyTime() {
        return new PrettyTime(LocaleContextHolder.getLocale());
    }
}
