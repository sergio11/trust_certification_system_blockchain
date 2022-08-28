package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public abstract class AbstractNotificationHandler<T extends AbstractNotificationEvent> {

    @Autowired
    private IMessageSourceResolverService messageSourceResolver;

    /**
     *
     * @param notification
     */
    public abstract void onHandle(T notification);

    /**
     *
     * @param key
     * @param locale
     * @param params
     * @return
     */
    protected String resolveString(final String key, final Locale locale, final Object[] params) {
        Assert.notNull(key, "Key can not be null");
        Assert.notNull(locale, "locale can not be null");
        return messageSourceResolver.resolver(key, params, locale);
    }

    /**
     *
     * @param key
     * @param locale
     * @return
     */
    protected String resolveString(final String key, final Locale locale) {
        return resolveString(key, locale, null);
    }

}
