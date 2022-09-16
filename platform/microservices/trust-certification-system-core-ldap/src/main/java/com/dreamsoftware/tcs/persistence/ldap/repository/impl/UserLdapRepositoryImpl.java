package com.dreamsoftware.tcs.persistence.ldap.repository.impl;

import com.dreamsoftware.tcs.config.properties.LdapProperties;
import com.dreamsoftware.tcs.persistence.exception.DirectoryException;
import com.dreamsoftware.tcs.persistence.ldap.entity.UserLdapEntity;
import com.dreamsoftware.tcs.persistence.ldap.mapper.UserEntityAttributesMapper;
import com.dreamsoftware.tcs.persistence.ldap.repository.IUserLdapRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserLdapRepositoryImpl implements IUserLdapRepository {

    private final LdapTemplate ldapTemplate;
    private final LdapProperties ldapProperties;

    /**
     *
     * @param uid
     * @return
     * @throws DirectoryException
     */
    @Override
    public UserLdapEntity findOneByUid(final String uid) throws DirectoryException {
        Assert.notNull(uid, "Uid can not be null");
        log.debug("UserLdapRepositoryImpl - findOneByUid: " + uid);
        try {
            final List<UserLdapEntity> users = ldapTemplate.search(ldapProperties.getLdapBaseUserSearch(), new EqualsFilter("uid", uid).encode(), new UserEntityAttributesMapper());
            if (users.isEmpty()) {
                throw new IllegalStateException(uid + " can not be found!", null);
            }
            return users.get(0);
        } catch (final Exception ex) {
            throw new DirectoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param uid
     * @param password
     * @return
     */
    @Override
    public boolean authenticate(final String uid, final String password) {
        Assert.notNull(uid, "Uid can not be null");
        Assert.notNull(password, "Uid can not be null");
        return ldapTemplate.authenticate(ldapProperties.getLdapBaseUserSearch(),
                new EqualsFilter("uid", uid).encode(), password);
    }

}
