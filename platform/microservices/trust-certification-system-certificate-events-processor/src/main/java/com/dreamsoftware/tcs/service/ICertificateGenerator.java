package com.dreamsoftware.tcs.service;

import java.io.File;

/**
 *
 * @author ssanchez
 */
public interface ICertificateGenerator {

    /**
     *
     * @param caName
     * @param studentName
     * @param courseName
     * @param qualification
     * @return
     * @throws Exception
     */
    File generate(String caName, String studentName, String courseName, Float qualification) throws Exception;

}
