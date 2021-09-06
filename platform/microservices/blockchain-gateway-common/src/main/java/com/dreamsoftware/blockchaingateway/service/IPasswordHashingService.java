package com.dreamsoftware.blockchaingateway.service;

/**
 *
 * @author ssanchez
 */
public interface IPasswordHashingService {

    /**
     * Hash clear text
     *
     * @param clearText
     * @return
     */
    String hash(final String clearText);

    /**
     * Check if clear text matches hash text
     *
     * @param clearText
     * @param hashText
     * @return
     */
    Boolean check(final String clearText, final String hashText);

}
