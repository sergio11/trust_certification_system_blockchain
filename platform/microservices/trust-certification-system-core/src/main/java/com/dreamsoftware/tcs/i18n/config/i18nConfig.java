package com.dreamsoftware.tcs.i18n.config;

import com.dreamsoftware.tcs.i18n.messages.LocaleMessageInterpolator;
import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import com.dreamsoftware.tcs.i18n.source.DBMessageSource;
import javax.annotation.PostConstruct;
import javax.validation.MessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
public class i18nConfig {

    private static final Logger logger = LoggerFactory.getLogger(i18nConfig.class);

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
        logger.debug("init i18nConfig ...");
    }
}
