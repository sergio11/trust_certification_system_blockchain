package com.dreamsoftware.tcs.web.security.config;

import com.dreamsoftware.tcs.web.security.filter.JwtAuthenticationTokenFilter;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author ssanchez
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Import(value = {CommonSecurityConfig.class, AdminsAuthenticationConfig.class, UsersAuthenticationConfig.class})
@Slf4j
public class WebSecurityConfig {

    /**
     * Povide Authentication Filter
     *
     * @return
     * @throws Exception
     */
    @Bean
    public JwtAuthenticationTokenFilter provideAuthenticationTokenFilter() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * Api Web Security Configuration Adapter
     *
     * @author Sergio Sánchez Sánchez
     * @since 0.0.1
     */
    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        /**
         * JWT Authentication Token Filter
         */
        @Autowired
        private JwtAuthenticationTokenFilter authenticationTokenFilter;

        /**
         * Base API any request
         */
        @Value("#{'${api.base.path}' + '${api.version}' + '/**'}")
        private String baseApiAnyRequets;

        /**
         * Configure Http Security
         *
         * @param http
         * @throws java.lang.Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.
                    headers()
                    .cacheControl()
                    .disable()
                    .and()
                    .requestMatchers()
                    .antMatchers(baseApiAnyRequets)
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                    .antMatchers("/api/v1/accounts/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException)
                            -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // Custom JWT based security filter
            http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    @PostConstruct
    public void init() {
        log.info("Init Web Security Configuration ...");
    }
}
