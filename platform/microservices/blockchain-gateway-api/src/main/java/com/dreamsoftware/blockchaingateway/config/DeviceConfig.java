package com.dreamsoftware.blockchaingateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;

/**
 *
 * @author ssanchez
 */
@Configuration
public class DeviceConfig {

    /**
     * Provide Device Resolver Handler Interceptor
     *
     * @return
     */
    @Bean
    public DeviceResolverHandlerInterceptor provideDeviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    /**
     * Provide Device Handler Method Argument Resolver
     *
     * @return
     */
    @Bean
    public DeviceHandlerMethodArgumentResolver provideDeviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }
}
