package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.service.IQRCodeGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service("qrCodeGenerator")
@RequiredArgsConstructor
public class QRCodeGeneratorImpl implements IQRCodeGenerator {

    private final static Integer DEFAULT_WIDTH = 250;
    private final static Integer DEFAULT_HEIGHT = 250;

    /**
     *
     * @param text
     * @return
     * @throws WriterException
     * @throws IOException
     */
    @Override
    public byte[] getQRCodeImage(final String text) throws WriterException, IOException {
        Assert.notNull(text, "Text can not be null");
        return getQRCodeImage(text, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     *
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    @Override
    public byte[] getQRCodeImage(final String text, int width, int height) throws WriterException, IOException {
        Assert.notNull(text, "Text can not be null");
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        final ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        final MatrixToImageConfig con = new MatrixToImageConfig();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        return pngOutputStream.toByteArray();
    }

}
