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
    String save(File fileToSave, Boolean deleteOnSave) throws IOException;

}
