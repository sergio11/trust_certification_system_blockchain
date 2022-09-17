package com.dreamsoftware.tcs.persistence.ldap.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLdapEntity {

    private String uidNumber;
    private String uid;
    private String cn;
    private String email;
}
