package com.dreamsoftware.blockchaingateway.service;

import java.io.File;

/**
 *
 * @author ssanchez
 */
public interface ISftpGateway {

    /**
     *
     * @param fileToSend
     */
    void sendFile(final File fileToSend);

    /**
     *
     * @param filePath
     */
    void syncRemoteFolderTo(final String filePath);

}
