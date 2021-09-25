package com.dreamsoftware.blockchaingateway.services;

/**
 *
 * @author ssanchez
 */
public interface IAccountsService {

    void restoreSecurityContextFor(final String jwtToken);
}
