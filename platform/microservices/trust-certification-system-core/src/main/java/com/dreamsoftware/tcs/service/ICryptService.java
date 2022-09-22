package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.exception.CryptException;

/**
 *
 * @author ssanchez
 */
public interface ICryptService {

    /**
     *
     * @param content
     * @return
     * @throws CryptException
     */
    String encrypt(final String content) throws CryptException;

    /**
     *
     * @param content
     * @return
     * @throws CryptException
     */
    String decrypt(final String content) throws CryptException;

}
