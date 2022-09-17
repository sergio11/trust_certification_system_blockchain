package com.dreamsoftware.tcs.persistence.ldap.repository;

import com.dreamsoftware.tcs.persistence.ldap.entity.UserLdapEntity;
import java.util.Optional;

/**
 *
 * @author ssanchez
 */
public interface IUserLdapRepository {

    /**
     *
     * @param uid
     * @return
     *
     */
    Optional<UserLdapEntity> findOneByUid(final String uid);

    /**
     *
     * @param uid
     * @param password
     * @return
     */

    boolean authenticate(final String uid, final String password);

}
