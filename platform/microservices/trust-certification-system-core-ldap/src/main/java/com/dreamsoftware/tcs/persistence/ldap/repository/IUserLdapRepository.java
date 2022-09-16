package com.dreamsoftware.tcs.persistence.ldap.repository;

import com.dreamsoftware.tcs.persistence.exception.DirectoryException;
import com.dreamsoftware.tcs.persistence.ldap.entity.UserLdapEntity;

/**
 *
 * @author ssanchez
 */
public interface IUserLdapRepository {

    /**
     *
     * @param uid
     * @return
     * @throws DirectoryException
     */
    UserLdapEntity findOneByUid(final String uid) throws DirectoryException;

    /**
     *
     * @param uid
     * @param password
     * @return
     */
    boolean authenticate(final String uid, final String password);

}
