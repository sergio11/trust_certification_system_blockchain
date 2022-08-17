package com.dreamsoftware.tcs.service;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ssanchez
 */
public interface ICertificateSigningService {

    /**
     *
     * @param certificateToSign
     * @return
     */
    byte[] sign(byte[] certificateToSign);

    /**
     *
     * @param certificateFileToSign
     * @return
     */
    byte[] sign(final File certificateFileToSign) throws IOException;
}
