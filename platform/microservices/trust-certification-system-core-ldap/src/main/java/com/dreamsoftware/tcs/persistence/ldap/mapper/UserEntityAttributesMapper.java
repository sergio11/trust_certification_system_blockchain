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

    private final String UID_NUMBER = "uidNumber";
    private final String UID = "uid";
    private final String CN = "cn";

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

        Attribute attribute = attributes.get(UID_NUMBER);
        if (attribute != null) {
            userAccount.setUidNumber((String) attribute.get());
        }
        attribute = attributes.get(UID);
        if (attribute != null) {
            userAccount.setUid((String) attribute.get());
        }
        attribute = attributes.get(CN);
        if (attribute != null) {
            userAccount.setCn((String) attribute.get());
        }
        return userAccount;
    }

}
