package com.dreamsoftware.blockchaingateway.web.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserStateEnum;

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
    String LANGUAGE = "LANGUAGE";
    String STATE = "STATE";
    String WALLET = "WALLET";

    T getUserId();

    String getPassword();

    String getName();

    String getSurname();

    String getLanguage();

    UserStateEnum getState();

    String getEmail();

    String getWallet();

}
