package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.config.properties.CertificateGeneratorProperties;
import com.dreamsoftware.tcs.dto.CertificateIssuedQRDataDTO;
import com.dreamsoftware.tcs.utils.WordReplacer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.service.ICertificateGeneratorService;
import com.dreamsoftware.tcs.service.ICryptService;
import com.dreamsoftware.tcs.service.IQRCodeGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service("certificateGeneratorImpl")
@RequiredArgsConstructor
public class CertificateGeneratorServiceImpl implements ICertificateGeneratorService {

    /**
     * Properties
     */
    private final CertificateGeneratorProperties certificateGeneratorProperties;

    /**
     * QR Code Generator
     */
    private final IQRCodeGenerator qrCodeGenerator;

    /**
     * Crypt Service
     */
    private final ICryptService cryptService;

    /**
     * Object Mapper
     */
    private final ObjectMapper objectMapper;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public File generate(final CertificationGenerationRequest request) throws Exception {
        Assert.notNull(request, "Certification Generation Request can not be null");
        final File certificateTemplate = getCertificateTemplate();
        final WordReplacer wordReplacer = new WordReplacer(certificateTemplate);
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCaNamePlaceholder(), request.getCaName());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getStudentNamePlaceholder(), request.getStudentName());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCourseNamePlaceholder(), request.getCourseName());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getStudentNamePlaceholder(), request.getQualification().toString());
        final File tempFile = File.createTempFile("tcs--", ".tmp");
        final File tempPdfDestFile = File.createTempFile("tcs--", ".tmp");
        final File fileSaved = wordReplacer.saveAndGetModdedFile(tempFile);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(fileSaved);
        try (final FileOutputStream out = new FileOutputStream(tempPdfDestFile, true)) {
            Docx4J.toPDF(wordMLPackage, out);
            tempFile.delete();
            configureWatermark(tempPdfDestFile);
            configureQRCode(tempPdfDestFile, request);
            return tempPdfDestFile;
        }

    }

    /**
     * Private Methods
     */
    /**
     * Get Certificate Template
     *
     * @return
     */
    private File getCertificateTemplate() {
        final File certificateTemplate = new File(certificateGeneratorProperties.getBaseFolder(), certificateGeneratorProperties.getTemplateFile());
        if (!certificateTemplate.exists() || !certificateTemplate.canRead()) {
            throw new IllegalStateException("Certification Template can not be found");
        }
        return certificateTemplate;
    }

    /**
     * Get Certificate Watermark File
     *
     * @return
     */
    private File getCertificateWatermarkFile() {
        final File certificateWatermark = new File(certificateGeneratorProperties.getBaseFolder(), certificateGeneratorProperties.getWatermarkFile());
        if (!certificateWatermark.exists() || !certificateWatermark.canRead()) {
            throw new IllegalStateException("Certification Watermark can not be found");
        }
        return certificateWatermark;
    }

    /**
     * Configure Watermark
     *
     * @param destFile
     * @throws IOException
     */
    private void configureWatermark(final File destFile) throws IOException {
        try (final PDDocument doc = PDDocument.load(destFile)) {
            HashMap<Integer, String> overlayGuide = new HashMap<>();
            final File certificateBackground = getCertificateWatermarkFile();
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                overlayGuide.put(i + 1, certificateBackground.getAbsolutePath());
            }
            try (Overlay overlay = new Overlay()) {
                overlay.setInputPDF(doc);
                overlay.overlay(overlayGuide).save(destFile);
            }
        }
    }

    /**
     * Configure QR Code
     *
     * @param destFile
     * @param request
     * @throws IOException
     */
    private void configureQRCode(final File destFile, final CertificationGenerationRequest request) throws IOException, WriterException {
        try (
                final PDDocument doc = PDDocument.load(destFile);
                final PDPageContentStream contents = new PDPageContentStream(doc, doc.getPage(0))) {
            // Generate Certificate QR Data
            final String certificateQrData = objectMapper.writeValueAsString(CertificateIssuedQRDataDTO.builder()
                    .caWalletHash(request.getCaWalletHash())
                    .studentWalletHash(request.getStudentName())
                    .courseId(request.getCourseId())
                    .build());
            final String certificateQrDataEncrypted = cryptService.encrypt(certificateQrData);
            log.debug("issueCertificate - QR data: " + certificateQrData);
            log.debug("issueCertificate - QR data encrypted: " + certificateQrDataEncrypted);
            // Generate QR Code
            final PDImageXObject qrCodeImage = PDImageXObject.createFromByteArray(doc, qrCodeGenerator.getQRCodeImage(cryptService.encrypt(certificateQrDataEncrypted)), "CertificationQRCode");
            contents.drawImage(qrCodeImage, 70, 250);
            doc.save(destFile);
        }
    }

}
