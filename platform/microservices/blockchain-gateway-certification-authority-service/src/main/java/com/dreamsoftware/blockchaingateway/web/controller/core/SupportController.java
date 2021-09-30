package com.dreamsoftware.blockchaingateway.web.controller.core;

import com.dreamsoftware.blockchaingateway.web.core.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.LocaleResolver;

/**
 *
 * @author ssanchez
 */
public class SupportController {

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
