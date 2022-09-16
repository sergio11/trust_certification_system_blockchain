package com.dreamsoftware.tcs.persistence.ldap.mapper;

import com.dreamsoftware.tcs.persistence.ldap.entity.UserLdapEntity;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.AttributesMapper;

/**
 *
 * @author ssanchez
 */
@Slf4j
public class UserEntityAttributesMapper implements AttributesMapper {

    /**
     *
     * @param attributes
     * @return
     * @throws NamingException
     */
    @Override
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        attributes.getIDs().asIterator().forEachRemaining((entryId -> {
            log.debug("AdminProviderImpl - entryId -> " + entryId);
        }));
        final UserLdapEntity userAccount = new UserLdapEntity();
        Attribute attribute = attributes.get("uid");
        if (attribute != null) {
            userAccount.setId((String) attribute.get());
        }
        attribute = attributes.get("mail");
        if (attribute != null) {
            userAccount.setEmail((String) attribute.get());
        }
        return userAccount;
    }

}
