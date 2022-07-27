package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.service.ICertificateGenerator;
import com.dreamsoftware.blockchaingateway.utils.WordReplacer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("certificateGeneratorImpl")
@RequiredArgsConstructor
public class CertificateGeneratorImpl implements ICertificateGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CertificateGeneratorImpl.class);
    private static final String CERTIFICATE_WATERMARK = "tcs_certificate_background.pdf";
    private static final String CERTIFICATE_TEMPLATE_FILE = "tcs_academic_certificate_template.docx";

    /**
     *
     * @param caName
     * @param studentName
     * @param qualification
     * @return
     * @throws Exception
     */
    @Override
    public File generate(String caName, String studentName, Float qualification) throws Exception {
        Assert.notNull(caName, "CA Name can not be null");
        Assert.notNull(studentName, "Student Name can not be null");
        Assert.notNull(qualification, "Qualification Name can not be null");
        Assert.notNull(caName, "CA Name can not be null");
        Assert.notNull(studentName, "Student Name can not be null");
        Assert.notNull(qualification, "Qualification Name can not be null");
        final File certificateTemplate = getFileForClasspathResource(CERTIFICATE_TEMPLATE_FILE);
        final WordReplacer wordReplacer = new WordReplacer(certificateTemplate);
        wordReplacer.replaceWordsInText("[[CA_NAME]]", caName);
        wordReplacer.replaceWordsInText("[[STUDE_NAME]]", studentName);
        wordReplacer.replaceWordsInText("[[STUDE_QUALIFICATION]]", qualification.toString());
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
     * Get File for Classpath Resource
     *
     * @param resourceFileName
     * @return
     * @throws IOException
     */
    private File getFileForClasspathResource(String resourceFileName) throws IOException {
        final Resource resource = new ClassPathResource(resourceFileName);
        return resource.getFile();
    }

    /**
     * Configure Watermark
     *
     * @param destFile
     * @throws IOException
     */
    private void configureWatermark(File destFile) throws IOException {
        try (final PDDocument doc = PDDocument.load(destFile)) {
            HashMap<Integer, String> overlayGuide = new HashMap<>();
            final File certificateBackground = getFileForClasspathResource(CERTIFICATE_WATERMARK);
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
