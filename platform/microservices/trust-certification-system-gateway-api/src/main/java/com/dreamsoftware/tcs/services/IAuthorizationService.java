package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import org.bson.types.ObjectId;

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
    ICommonUserDetailsAware<ObjectId> getUserDetails();
}
