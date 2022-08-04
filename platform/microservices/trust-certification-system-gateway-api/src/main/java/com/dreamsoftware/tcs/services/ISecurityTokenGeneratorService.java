package com.dreamsoftware.tcs.services;

/**
 *
 * @author ssanchez
 */
public interface ISecurityTokenGeneratorService {

    /**
     * Generate Token
     *
     * @param id
     * @return
     */
    String generateToken(String id);
}
