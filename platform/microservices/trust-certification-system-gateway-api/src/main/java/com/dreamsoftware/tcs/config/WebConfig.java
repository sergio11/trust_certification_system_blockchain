package com.dreamsoftware.tcs.config;

import java.util.List;
import javax.validation.MessageInterpolator;
import org.springframework.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Web Configuration
 *
 * @author ssanchez
 */
@Configuration
@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;
    private final DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor;
    private final DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver;
    private final MessageInterpolator messageInterpolator;

    /**
     * Configure Validator Factory
     *
     * @return
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setMessageInterpolator(messageInterpolator);
        return factory;
    }

    /**
     * Add View Controllers
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "swagger-ui.html");
    }

    /**
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(deviceHandlerMethodArgumentResolver);
    }

    /**
     * Configure Interceptor registry
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(deviceResolverHandlerInterceptor);
    }

    /**
     * Provide Shallow ETag Header Filter
     *
     * @return
     */
    @Bean
    public ShallowEtagHeaderFilter provideShallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
