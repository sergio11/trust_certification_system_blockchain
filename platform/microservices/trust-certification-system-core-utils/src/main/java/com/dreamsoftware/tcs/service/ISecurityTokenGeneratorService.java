package com.dreamsoftware.tcs.service;

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
