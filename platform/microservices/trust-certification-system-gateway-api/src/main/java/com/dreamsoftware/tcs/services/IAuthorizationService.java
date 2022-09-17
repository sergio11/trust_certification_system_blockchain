package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author ssanchez
 */
public interface IAuthorizationService {

    /**
     * Is Admin
     *
     * @return
     */
    Boolean isAdmin();

    /**
     * Is Ca Or Student
     *
     * @return
     */
    Boolean isCAOrStudent();

    /**
     * Is CA
     *
     * @return
     */
    Boolean isCA();

    /**
     * Is Student
     *
     * @return
     */
    Boolean isStudent();

    /**
     * Is The Authenticated User
     *
     * @param id
     * @return
     */
    Boolean isTheAuthenticatedUser(String id);

    /**
     * Get User Details
     *
     * @return
     */
    ICommonUserDetailsAware<String> getUserDetails();

    /**
     *
     * @param authority
     * @param authorities
     * @return
     */
    Boolean hasAuthority(final AuthorityEnum authority, final Collection<? extends GrantedAuthority> authorities);
}
