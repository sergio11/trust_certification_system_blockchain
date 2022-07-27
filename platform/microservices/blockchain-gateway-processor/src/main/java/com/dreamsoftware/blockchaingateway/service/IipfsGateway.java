package com.dreamsoftware.blockchaingateway.service;

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
     * @return
     * @throws IOException
     */
    String save(File fileToSave) throws IOException;

}
