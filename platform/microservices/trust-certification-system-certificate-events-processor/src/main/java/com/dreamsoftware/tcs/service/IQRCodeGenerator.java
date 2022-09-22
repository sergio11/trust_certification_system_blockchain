package com.dreamsoftware.tcs.service;

import com.google.zxing.WriterException;
import java.io.IOException;

/**
 *
 * @author ssanchez
 */
public interface IQRCodeGenerator {

    /**
     *
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    byte[] getQRCodeImage(String text) throws WriterException, IOException;

    /**
     *
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException;
}
