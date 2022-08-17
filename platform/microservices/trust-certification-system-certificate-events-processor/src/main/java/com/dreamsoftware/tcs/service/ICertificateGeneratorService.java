package com.dreamsoftware.tcs.service;

import java.io.File;

/**
 * Certificate Generator Service
 *
 * @author ssanchez
 */
public interface ICertificateGeneratorService {

    /**
     *
     * @param caName
     * @param studentName
     * @param courseName
     * @param qualification
     * @return
     * @throws Exception
     */
    File generate(String caName, String studentName, String courseName, Long qualification) throws Exception;

}
