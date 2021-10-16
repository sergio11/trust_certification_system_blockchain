package com.dreamsoftware.blockchaingateway.i18n.messages;

import com.dreamsoftware.blockchaingateway.i18n.service.I18NService;
import com.dreamsoftware.blockchaingateway.i18n.service.IMessageSourceResolverService;
import java.util.Locale;
import java.util.Map;
import javax.validation.MessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ssanchez
 */
public class LocaleMessageInterpolator implements MessageInterpolator {

    private static final Logger logger = LoggerFactory.getLogger(LocaleMessageInterpolator.class);

    private final I18NService i18nService;
    private final IMessageSourceResolverService messageSourceResolver;

    protected final String BRACE_OPEN = "\\{";
    protected final String BRACE_CLOSE = "\\}";

    /**
     *
     * @param i18nService
     * @param messageSourceResolver
     */
    public LocaleMessageInterpolator(I18NService i18nService, IMessageSourceResolverService messageSourceResolver) {
        this.i18nService = i18nService;
        this.messageSourceResolver = messageSourceResolver;
    }

    @Override
    public String interpolate(String message, Context context) {
        return interpolate(message, context, i18nService.getDefault());
    }

    @Override
    public String interpolate(String string, Context context, Locale locale) {

        final String messageKey = context.getConstraintDescriptor().getAttributes().get("message").toString()
                .replaceAll(BRACE_OPEN, "").replaceAll(BRACE_CLOSE, "");

        logger.debug("interpolate key -> " + messageKey);

        String message = messageSourceResolver.resolver(messageKey, locale);

        // Replace Arguments
        Map<String, Object> attributes = context.getConstraintDescriptor().getAttributes();
        for (String key : attributes.keySet()) {
            String value = attributes.get(key).toString();
            key = BRACE_OPEN + key + BRACE_CLOSE;
            message = message.replaceAll(key, value);
        }
        return message;
    }
}
