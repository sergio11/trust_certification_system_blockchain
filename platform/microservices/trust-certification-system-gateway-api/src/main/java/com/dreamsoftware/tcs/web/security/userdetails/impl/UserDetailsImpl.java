package com.dreamsoftware.tcs.web.security.userdetails.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import java.util.Collection;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author ssanchez
 * @param <T>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl<T> implements ICommonUserDetailsAware<T> {

    private static final long serialVersionUID = 1L;

    private T id;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String language;
    private UserStateEnum state;
    private UserAccountStateEnum accountState;
    private String walletHash;
    private Set<GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountState != UserAccountStateEnum.REMOVED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return state == UserStateEnum.VALIDATED && accountState == UserAccountStateEnum.ENABLED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state == UserStateEnum.VALIDATED && accountState == UserAccountStateEnum.ENABLED;
    }

    @Override
    public T getUserId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public UserAccountStateEnum getAccountState() {
        return accountState;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getWalletHash() {
        return walletHash;
    }

}
