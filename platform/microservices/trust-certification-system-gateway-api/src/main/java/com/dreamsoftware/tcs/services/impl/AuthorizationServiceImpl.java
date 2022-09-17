package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.services.IAuthorizationService;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import io.jsonwebtoken.lang.Assert;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Authorization Service Impl
 */
@Service("authorizationService")
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements IAuthorizationService {

    /**
     *
     * @return
     */
    @Override
    public Boolean isAdmin() {
        return hasAuthority(AuthorityEnum.ROLE_ADMIN);
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean isCA() {
        return hasAuthority(AuthorityEnum.ROLE_CA);
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean isStudent() {
        return hasAuthority(AuthorityEnum.ROLE_STUDENT);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Boolean isTheAuthenticatedUser(final String id) {
        Boolean isTheAuthenticatedUser = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            ICommonUserDetailsAware<String> userDetails = (ICommonUserDetailsAware<String>) auth.getPrincipal();
            isTheAuthenticatedUser = userDetails.getUserId().equals(id);
        }
        return isTheAuthenticatedUser;
    }

    /**
     *
     * @return
     */
    @Override
    public ICommonUserDetailsAware<String> getUserDetails() {
        ICommonUserDetailsAware<String> userDetails = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            userDetails = (ICommonUserDetailsAware<String>) auth.getPrincipal();
        }
        return userDetails;
    }

    /**
     *
     * @param authority
     * @param authorities
     * @return
     */
    @Override
    public Boolean hasAuthority(final AuthorityEnum authority, final Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authority, "authority can not be null");
        Assert.notNull(authorities, "authorities can not be null");
        return authorities.contains(new SimpleGrantedAuthority(authority.name()));
    }

    /**
     * Private Method
     */
    /**
     * Has Authority
     *
     * @param authority
     * @return
     */
    private Boolean hasAuthority(final AuthorityEnum authority) {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(authority.name())));
    }

}
