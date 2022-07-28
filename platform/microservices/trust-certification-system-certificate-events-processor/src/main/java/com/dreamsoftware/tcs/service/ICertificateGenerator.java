package com.dreamsoftware.tcs.service;

import java.io.File;

/**
 *
 * @author ssanchez
 */
public interface ICertificateGenerator {

    File generate(String caName, String studentName, Float qualification) throws Exception;

}
