package com.dreamsoftware.blockchaingateway.web.security.userdetails;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AuthorityEnum;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public interface ICommonUserDetailsAware<T> extends UserDetails {

    String ID = "id";
    String PASSWORD = "PASSWORD";
    String NAME = "NAME";
    String SURNAME = "SURNAME";
    String EMAIL = "EMAIL";

    T getUserId();

    String getPassword();

    String getName();

    String getSurname();

    String getEmail();

}
