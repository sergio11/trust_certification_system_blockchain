package com.dreamsoftware.tcs.web.security.userdetails;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import org.springframework.security.core.userdetails.UserDetails;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;

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
    String WALLET_HASH = "WALLET_HASH";

    T getUserId();

    String getPassword();

    String getName();

    String getSurname();

    String getLanguage();

    UserStateEnum getState();

    UserAccountStateEnum getAccountState();

    String getEmail();

    String getWalletHash();

}
