package com.dreamsoftware.tcs.web.security.provider.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author ssanchez
 */
public class SocialProviderAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final String socialKey;

    public SocialProviderAuthenticationToken(Object principal, String socialKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.socialKey = socialKey;
    }

    public SocialProviderAuthenticationToken(Object principal, String socialKey) {
        this(principal, socialKey, null);
    }

    public SocialProviderAuthenticationToken(String socialKey) {
        this(null, socialKey, null);
    }

    @Override
    public Object getCredentials() {
        return socialKey;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
