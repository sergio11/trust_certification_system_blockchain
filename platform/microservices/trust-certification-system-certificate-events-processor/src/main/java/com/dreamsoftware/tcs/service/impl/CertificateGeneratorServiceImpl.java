package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.config.properties.CertificateGeneratorProperties;
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
import lombok.extern.slf4j.Slf4j;

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
     *
     * @param caName
     * @param studentName
     * @param courseName
     * @param qualification
     * @return
     * @throws Exception
     */
    @Override
    public File generate(final String caName, final String studentName, final String courseName, final Long qualification) throws Exception {
        Assert.notNull(caName, "CA Name can not be null");
        Assert.notNull(studentName, "Student Name can not be null");
        Assert.notNull(courseName, "Course Name can not be null");
        Assert.notNull(qualification, "Qualification Name can not be null");
        log.debug("Generate Certificate for " + studentName);
        final File certificateTemplate = getCertificateTemplate();
        final WordReplacer wordReplacer = new WordReplacer(certificateTemplate);
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCaNamePlaceholder(), caName);
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getStudentNamePlaceholder(), studentName);
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getCourseNamePlaceholder(), courseName);
        wordReplacer.replaceWordsInText(certificateGeneratorProperties.getStudentNamePlaceholder(), qualification.toString());
        final File tempFile = File.createTempFile("tcs--", ".tmp");
        final File tempPdfDestFile = File.createTempFile("tcs--", ".tmp");
        final File fileSaved = wordReplacer.saveAndGetModdedFile(tempFile);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(fileSaved);
        try (final FileOutputStream out = new FileOutputStream(tempPdfDestFile, true)) {
            Docx4J.toPDF(wordMLPackage, out);
            tempFile.delete();
            configureWatermark(tempPdfDestFile);
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

}
