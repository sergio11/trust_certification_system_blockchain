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

    private String id;
    private String name;
    private String surname;
    private String email;
}
