package com.dreamsoftware.tcs.persistence.nosql.repository;

/**
 *
 * @author ssanchez
 */
public interface AuthenticationProviderRepositoryCustom {

    /**
     *
     * @param token
     * @param key
     */
    void updateAuthenticationProviderTokenForkey(final String token, final String key);

}
