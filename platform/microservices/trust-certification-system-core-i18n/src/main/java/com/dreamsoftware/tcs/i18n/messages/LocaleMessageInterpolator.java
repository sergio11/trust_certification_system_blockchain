package com.dreamsoftware.tcs.i18n.messages;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import java.util.Locale;
import java.util.Map;
import javax.validation.MessageInterpolator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssanchez
 */
@Slf4j
@RequiredArgsConstructor
public class LocaleMessageInterpolator implements MessageInterpolator {

    private final I18NService i18nService;
    private final IMessageSourceResolverService messageSourceResolver;

    protected final String BRACE_OPEN = "\\{";
    protected final String BRACE_CLOSE = "\\}";

    @Override
    public String interpolate(String message, Context context) {
        return interpolate(message, context, i18nService.getDefault());
    }

    @Override
    public String interpolate(String string, Context context, Locale locale) {

        final String messageKey = context.getConstraintDescriptor().getAttributes().get("message").toString()
                .replaceAll(BRACE_OPEN, "").replaceAll(BRACE_CLOSE, "");

        log.debug("interpolate key -> " + messageKey);

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
