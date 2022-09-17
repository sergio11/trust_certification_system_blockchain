package com.dreamsoftware.tcs.web.security.filter;

import com.dreamsoftware.tcs.services.IAccountsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT Authentication Token Filter
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final String PREFIX = "Bearer ";

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Autowired
    private IAccountsService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            if (existeJWTToken(request, response)) {
                // Get JWT token from HTTP Header
                String jwtToken = request.getHeader(tokenHeader).replace(PREFIX, "");

                // Restore Security Context
                if (StringUtils.isNotEmpty(jwtToken)) {
                    accountService.restoreSecurityContextFor(jwtToken);
                }
            }
        }

        // call Next filter
        filterChain.doFilter(request, response);

    }

    private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(tokenHeader);
        return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX));
    }

}
