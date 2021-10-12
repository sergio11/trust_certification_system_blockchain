package com.dreamsoftware.blockchaingateway.i18n.resolver;

import com.dreamsoftware.blockchaingateway.services.I18NService;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 *
 * @author ssanchez
 */
public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {

    private static Logger logger = LoggerFactory.getLogger(SmartLocaleResolver.class);

    private final I18NService i18NService;

    @Autowired
    public SmartLocaleResolver(I18NService i18nService) {
        super();
        i18NService = i18nService;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        logger.debug("Accept-Language -> " + request.getHeader("Accept-Language"));
        return i18NService.parseLocaleOrDefault(request.getHeader("Accept-Language"));
    }
}
