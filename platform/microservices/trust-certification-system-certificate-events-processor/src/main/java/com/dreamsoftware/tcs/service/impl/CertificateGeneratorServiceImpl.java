package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.config.properties.CertificateGeneratorProperties;
import com.dreamsoftware.tcs.model.CertificateGenerated;
import com.dreamsoftware.tcs.model.CertificationGenerationRequest;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateTypeEnum;
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
import com.dreamsoftware.tcs.service.ICertificateSigningService;
import com.dreamsoftware.tcs.service.ICryptService;
import com.dreamsoftware.tcs.service.IQRCodeGenerator;
import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * @author ssanchez
 */
@Slf4j
@Service("certificateGeneratorImpl")
@RequiredArgsConstructor
public class CertificateGeneratorServiceImpl implements ICertificateGeneratorService {

    private final static Integer DEFAULT_DPI = 600;
    private final static Integer DEFAULT_QR_CODE_WIDTH = 150;
    private final static Integer DEFAULT_QR_CODE_HEIGHT = 150;
    private final static Integer DEFAULT_QR_POSITION_X = 50;
    private final static Integer DEFAULT_QR_POSITION_Y = 34;

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
     * Certificate Signing Service
     */
    private final ICertificateSigningService certificateSigningService;

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public CertificateGenerated generate(final CertificationGenerationRequest request) throws Exception {
        Assert.notNull(request, "Certification Generation Request can not be null");
        final UUID certificateId = UUID.randomUUID();
        final File certificateTemplate = getCertificateTemplateFile(request.getType());
        final File certificateFileConfigured = replaceCertificatePlaceholders(certificateTemplate, request);
        final File certificatePdfFile = convertToPdf(certificateFileConfigured);
        configureBackground(certificatePdfFile, request.getType());
        configureQRCode(certificatePdfFile, certificateId);
        signCertificate(certificatePdfFile);
        final File certificateImageFile = generateCertificateImage(certificatePdfFile);
        return CertificateGenerated.builder()
                .id(certificateId)
                .image(certificateImageFile)
                .file(certificatePdfFile)
                .build();
    }

    /**
     * Private Methods
     */


    /**
     * Get Certificate File
     * @param fileName
     * @return
     */
    private File getCertificateFile(final String fileName) {
        final File certificateTemplate = new File(certificateGeneratorProperties.getBaseFolder(), fileName);
        log.debug("certificateTemplate -> " + certificateTemplate.getAbsolutePath());
        if (!certificateTemplate.exists() || !certificateTemplate.canRead()) {
            throw new IllegalStateException("Certification File can not be found");
        }
        return certificateTemplate;
    }

    /**
     * Get Certificate Background File
     *
     * @return
     */
    private File getCertificateBackgroundFile(final CertificateTypeEnum certificateType) {
        return getCertificateFile(certificateType == CertificateTypeEnum.ACHIEVEMENT ?
                certificateGeneratorProperties.getBackgroundAchievement() :
                certificateGeneratorProperties.getBackgroundAttendance());
    }

    /**
     * Get Certificate Template File
     *
     * @return
     */
    private File getCertificateTemplateFile(final CertificateTypeEnum certificateType) {
        return getCertificateFile(certificateType == CertificateTypeEnum.ACHIEVEMENT ?
                certificateGeneratorProperties.getAchievementCertificateTemplate() :
                certificateGeneratorProperties.getAttendanceCertificateTemplate());
    }

    /**
     * @param certificateTemplate
     * @param request
     * @return
     * @throws IOException
     * @throws Exception
     */
    private File replaceCertificatePlaceholders(final File certificateTemplate, final CertificationGenerationRequest request) throws IOException, Exception {
        Assert.notNull(certificateTemplate, "Certificate Template can not be null");
        Assert.notNull(request, "request can not be null");
        final WordReplacer wordReplacer = new WordReplacer(certificateTemplate);
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCaNamePlaceholder(), request.getCaName());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getStudentNamePlaceholder(), request.getStudentName());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCourseNamePlaceholder(), request.getCourseName());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getExecutiveDirectorPlaceholder(), request.getExecutiveDirector());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getStudentQualificationPlaceholder(), request.getQualification().toString());
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCertificateDatePlaceholder(), new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        final File tempFile = File.createTempFile("tcs--", ".tmp");
        return wordReplacer.saveAndGetModdedFile(tempFile);
    }

    /**
     * @param certificateFile
     * @return
     * @throws IOException
     * @throws Docx4JException
     * @throws WriterException
     */
    private File convertToPdf(final File certificateFile) throws IOException, Docx4JException, WriterException {
        final File pdfDestFile = File.createTempFile("tcs--", ".tmp");
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(certificateFile);
        try (final FileOutputStream out = new FileOutputStream(pdfDestFile, true)) {
            Docx4J.toPDF(wordMLPackage, out);
            certificateFile.delete();
            return pdfDestFile;
        }
    }

    /**
     * Configure Background
     *
     * @param destFile
     * @throws IOException
     */
    private void configureBackground(final File destFile, final CertificateTypeEnum certificateTypeEnum) throws IOException {
        try (final PDDocument doc = PDDocument.load(destFile)) {
            log.debug("configureBackground CALLED!");
            HashMap<Integer, String> overlayGuide = new HashMap<>();
            final File certificateBackground = getCertificateBackgroundFile(certificateTypeEnum);
            log.debug("doc.getNumberOfPages() -> " + doc.getNumberOfPages());
            log.debug("certificateBackground path -> " + certificateBackground.getAbsolutePath());
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                overlayGuide.put(i + 1, certificateBackground.getAbsolutePath());
            }
            try (Overlay overlay = new Overlay()) {
                overlay.setInputPDF(doc);
                overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
                log.debug("save file with overlay at -> " + destFile.getAbsolutePath());
                overlay.overlay(overlayGuide).save(destFile);
            }
        }
    }

    /**
     * Configure QR Code
     *
     * @param destFile
     * @param certificateId
     * @throws IOException
     */
    private void configureQRCode(final File destFile, final UUID certificateId) throws IOException, WriterException {
        try (final PDDocument doc = PDDocument.load(destFile)) {
            try (PDPageContentStream contents = new PDPageContentStream(doc, doc.getPage(0), PDPageContentStream.AppendMode.APPEND, true)) {
                final String certificateQrDataEncrypted = cryptService.encrypt(certificateId.toString());
                log.debug("issueCertificate - QR data: " + certificateId);
                log.debug("issueCertificate - QR data encrypted: " + certificateQrDataEncrypted);
                final byte[] qrCodeData = qrCodeGenerator.getQRCodeImage(certificateQrDataEncrypted, DEFAULT_QR_CODE_WIDTH, DEFAULT_QR_CODE_HEIGHT);
                final PDImageXObject qrCodeImage = PDImageXObject.createFromByteArray(doc, qrCodeData, "CertificationQRCode");
                qrCodeImage.setWidth(DEFAULT_QR_CODE_WIDTH);
                qrCodeImage.setHeight(DEFAULT_QR_CODE_HEIGHT);
                contents.drawImage(qrCodeImage, DEFAULT_QR_POSITION_X, DEFAULT_QR_POSITION_Y);
            }
            doc.save(destFile);
        }
    }

    /**
     * @param certificateFile
     * @return
     * @throws IOException
     */
    private File generateCertificateImage(final File certificateFile) throws IOException {
        try (final PDDocument doc = PDDocument.load(certificateFile)) {
            final PDFRenderer pdfRenderer = new PDFRenderer(doc);
            BufferedImage bImage = pdfRenderer.renderImageWithDPI(0, DEFAULT_DPI, ImageType.RGB);
            final File imageDestFile = File.createTempFile("tcs--", ".tmp");
            ImageIO.write(bImage, "png", imageDestFile);
            return imageDestFile;
        }

    }

    /**
     * @param certificateFile
     * @return
     * @throws IOException
     */
    private File signCertificate(final File certificateFile) throws IOException {
        // Sign Certificate
        byte[] certificateFileSignedBytes = certificateSigningService.sign(certificateFile);
        // Write data into certificate file
        FileUtils.writeByteArrayToFile(certificateFile, certificateFileSignedBytes, false);
        return certificateFile;
    }

}
