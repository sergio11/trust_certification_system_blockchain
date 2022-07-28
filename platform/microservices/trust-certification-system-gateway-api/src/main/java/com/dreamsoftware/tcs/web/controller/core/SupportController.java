package com.dreamsoftware.tcs.web.controller.core;

import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import com.dreamsoftware.tcs.web.core.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.LocaleResolver;

/**
 *
 * @author ssanchez
 */
public class SupportController {

    /**
     * Message Source Resolver
     */
    @Autowired
    protected IMessageSourceResolverService messageSourceResolver;

    /**
     * Response Helper
     */
    @Autowired
    protected ResponseHelper responseHelper;

    /**
     * Application Event Publisher
     */
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    /**
     * Locale Resolver
     */
    @Autowired
    protected LocaleResolver localeResolver;
}
