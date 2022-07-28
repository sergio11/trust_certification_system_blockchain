package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.services.IAuthorizationService;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * AuthorizationServiceImpl
 */
@Service("authorizationService")
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements IAuthorizationService {

    @Override
    public Boolean isAdmin() {
        return hasAuthority(AuthorityEnum.ROLE_ADMIN);
    }

    @Override
    public Boolean isCA() {
        return hasAuthority(AuthorityEnum.ROLE_CA);
    }

    @Override
    public Boolean isStudent() {
        return hasAuthority(AuthorityEnum.ROLE_STUDENT);
    }

    @Override
    public Boolean isTheAuthenticatedUser(String id) {
        Boolean isTheAuthenticatedUser = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectId.isValid(id) && !(auth instanceof AnonymousAuthenticationToken)) {
            ICommonUserDetailsAware<ObjectId> userDetails = (ICommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            isTheAuthenticatedUser = userDetails.getUserId().equals(new ObjectId(id));
        }
        return isTheAuthenticatedUser;
    }

    @Override
    public ICommonUserDetailsAware<ObjectId> getUserDetails() {
        ICommonUserDetailsAware<ObjectId> userDetails = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            userDetails = (ICommonUserDetailsAware<ObjectId>) auth.getPrincipal();
        }
        return userDetails;
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
    private Boolean hasAuthority(AuthorityEnum authority) {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(authority.name())));
    }
}
