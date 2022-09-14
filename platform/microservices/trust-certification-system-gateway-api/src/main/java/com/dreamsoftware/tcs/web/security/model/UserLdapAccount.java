package com.dreamsoftware.tcs.web.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLdapAccount {

    private String id;
    private String name;
    private String surname;
    private String email;
}
