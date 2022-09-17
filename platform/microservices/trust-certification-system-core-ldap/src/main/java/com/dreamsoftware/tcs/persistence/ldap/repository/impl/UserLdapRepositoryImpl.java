package com.dreamsoftware.tcs.persistence.ldap.repository.impl;

import com.dreamsoftware.tcs.config.properties.LdapProperties;
import com.dreamsoftware.tcs.persistence.ldap.entity.UserLdapEntity;
import com.dreamsoftware.tcs.persistence.ldap.mapper.UserEntityAttributesMapper;
import com.dreamsoftware.tcs.persistence.ldap.repository.IUserLdapRepository;
import java.util.List;
import java.util.Optional;
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
     */
    @Override
    public Optional<UserLdapEntity> findOneByUid(final String uid) {
        Assert.notNull(uid, "Uid can not be null");
        log.debug("UserLdapRepositoryImpl - findOneByUid: " + uid);
        final List<UserLdapEntity> users = ldapTemplate.search(ldapProperties.getLdapBaseUserSearch(), new EqualsFilter("uid", uid).encode(), new UserEntityAttributesMapper());
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
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
