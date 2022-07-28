package com.dreamsoftware.tcs.i18n.service.impl;

import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import java.util.Locale;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class MessageSourceResolverServiceImpl implements IMessageSourceResolverService {

    private Logger logger = LoggerFactory.getLogger(MessageSourceResolverServiceImpl.class);

    private final MessageSource messageSource;

    /**
     * Resolver
     *
     * @param key
     * @return String
     */
    @Override
    public String resolver(String key) {
        Assert.notNull(key, "key can not be null");
        return messageSource.getMessage(key, new Object[]{},
                LocaleContextHolder.getLocale());
    }

    /**
     * Resolver
     *
     * @param key
     * @param locale
     * @return String
     */
    @Override
    public String resolver(String key, Locale locale) {
        Assert.notNull(key, "key can not be null");
        Assert.notNull(locale, "locale can not be null");
        return messageSource.getMessage(key, new Object[]{}, locale);
    }

    /**
     * Resolver
     *
     * @param key
     * @param params
     * @return String
     */
    @Override
    public String resolver(String key, Object[] params) {
        Assert.notNull(key, "key can not be null");
        Assert.notEmpty(params, "You must provide parameters");
        return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
    }

    /**
     * Resolver
     *
     * @param key
     * @param params
     * @param locale
     * @return String
     */
    @Override
    public String resolver(String key, Object[] params, Locale locale) {
        Assert.notNull(key, "key can not be null");
        Assert.notEmpty(params, "You must provide parameters");
        Assert.notNull(locale, "locale can not be null");
        return messageSource.getMessage(key, params, locale);
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(messageSource, "Message Source can not be null");
    }
}
