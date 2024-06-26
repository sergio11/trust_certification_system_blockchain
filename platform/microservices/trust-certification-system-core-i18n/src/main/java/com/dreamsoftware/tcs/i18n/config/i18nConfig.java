package com.dreamsoftware.tcs.i18n.config;

import com.dreamsoftware.tcs.i18n.messages.LocaleMessageInterpolator;
import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import com.dreamsoftware.tcs.i18n.source.DBMessageSource;
import javax.annotation.PostConstruct;
import javax.validation.MessageInterpolator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
@Slf4j
public class i18nConfig {

    /**
     * Provide Message Source to Spring Context
     *
     * @return MessageSource
     */
    @Bean(name = "messageSource")
    public MessageSource provideMessageSource() {
        return new DBMessageSource();
    }

    /**
     * Provide Message Interpolator
     *
     * @param i18nService
     * @param messageSourceResolver
     * @return
     */
    @Bean
    public MessageInterpolator provideMessageInterpolator(final I18NService i18nService, final IMessageSourceResolverService messageSourceResolver) {
        return new LocaleMessageInterpolator(i18nService, messageSourceResolver);
    }

    @PostConstruct
    protected void init() {
        log.debug("init i18nConfig ...");
    }
}
