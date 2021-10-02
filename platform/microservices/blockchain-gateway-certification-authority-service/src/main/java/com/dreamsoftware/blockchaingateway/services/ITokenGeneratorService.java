package com.dreamsoftware.blockchaingateway.services;

/**
 *
 * @author ssanchez
 */
public interface ITokenGeneratorService {

    /**
     * Generate Token
     *
     * @param id
     * @return
     */
    String generateToken(String id);
}
