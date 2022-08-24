package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.i18n.config.properties.i18nProperties;
import com.dreamsoftware.tcs.i18n.resolver.SmartLocaleResolver;
import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.web.converter.mediatype.JsonMergePatchHttpMessageConverter;
import com.dreamsoftware.tcs.web.converter.mediatype.JsonPatchHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.validation.MessageInterpolator;
import org.springframework.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
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

    private final i18nProperties i18nProperties;
    private final DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor;
    private final DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver;
    private final MessageInterpolator messageInterpolator;
    private final ObjectMapper objectMapper;

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
     * Configure Message Converters
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new JsonMergePatchHttpMessageConverter());
        converters.add(new JsonPatchHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        converters.add(new ByteArrayHttpMessageConverter());
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
        registry.addInterceptor(provideLocaleChangeInterceptor());
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

    /**
     * Provide Locale Change Interceptor
     *
     * @return LocaleChangeInterceptor
     */
    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor provideLocaleChangeInterceptor() {
        LocaleChangeInterceptor lcInterceptor = new LocaleChangeInterceptor();
        lcInterceptor.setParamName(i18nProperties.getParamName());
        return lcInterceptor;
    }

    /**
     * Provide Locale Resolver The LocaleResolver interface has implementations
     * that determine the current locale based on the session, cookies, the
     * Accept-Language header, or a fixed value.
     *
     * @param i18nService
     * @return LocaleResolver
     */
    @Bean
    @Primary
    public LocaleResolver customLocaleResolver(final I18NService i18nService) {
        return new SmartLocaleResolver(i18nService);
    }

    /**
     * Provide Pretty time as suitable date format utility
     *
     * @return PrettyTime
     */
    @Bean(name = "prettyTime")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrettyTime providePrettyTime() {
        return new PrettyTime(LocaleContextHolder.getLocale());
    }
}
