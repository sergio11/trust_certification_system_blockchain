package com.dreamsoftware.tcs.service;

import java.io.File;
import java.io.IOException;

/**
 * IPFS Gateway
 *
 * @author ssanchez
 */
public interface IipfsGateway {

    /**
     * Save File
     *
     * @param fileToSave
     * @param deleteOnSave
     * @return
     * @throws java.io.IOException
     */
    String save(final File fileToSave, final Boolean deleteOnSave) throws IOException;

    /**
     *
     * @param hashBase58
     * @return
     * @throws Exception
     */
    byte[] get(final String hashBase58) throws Exception;

}
