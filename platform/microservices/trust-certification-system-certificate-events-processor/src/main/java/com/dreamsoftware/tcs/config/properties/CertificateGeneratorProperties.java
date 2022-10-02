package com.dreamsoftware.tcs.config.properties;

import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Data
@Component
@RefreshScope
public class CertificateGeneratorProperties {

    /**
     * Base Folder
     */
    @Value("${certificateGenerator.base-folder}")
    private String baseFolder;

    /**
     * Background innovate
     */
    @Value("${certificateGenerator.background.innovate}")
    private String backgroundInnovate;

    /**
     * Background Professional
     */
    @Value("${certificateGenerator.background.professional}")
    private String backgroundProfessional;

    /**
     * Background Standard
     */
    @Value("${certificateGenerator.background.standard}")
    private String backgroundStandard;

    /**
     * Template File
     */
    @Value("${certificateGenerator.template-file}")
    private String templateFile;

    /**
     * Timestamp Authority
     */
    @Value("${certificateGenerator.timestamp-authority}")
    private String timestampAuthority;

    /**
     * CA Name Placeholder
     */
    @Value("${certificateGenerator.placeholders.ca-name}")
    private String caNamePlaceholder;

    /**
     * Student Name Placeholder
     */
    @Value("${certificateGenerator.placeholders.student-name}")
    private String studentNamePlaceholder;

    /**
     * Course Name Placeholder
     */
    @Value("${certificateGenerator.placeholders.course-name}")
    private String courseNamePlaceholder;

    /**
     * Student Qualification Placeholder
     */
    @Value("${certificateGenerator.placeholders.student-qualification}")
    private String studentQualificationPlaceholder;

    /**
     * Certificate Date Placeholder
     */
    @Value("${certificateGenerator.placeholders.certificate-date}")
    private String certificateDatePlaceholder;

    /**
     * Keystore Path
     */
    @Value("${certificateGenerator.keystore.file}")
    private String keystoreFile;

    /**
     * Keystore Password
     */
    @Value("${certificateGenerator.keystore.password}")
    private String keystorePassword;

    /**
     * Keystore Certificate Alias
     */
    @Value("${certificateGenerator.keystore.certificate-alias}")
    private String keystoreCertificateAlias;

    /**
     * Keystore Type
     */
    @Value("${certificateGenerator.keystore.type}")
    private String keystoreType;

    /**
     * Signature Name
     */
    @Value("${certificateGenerator.signature.name}")
    private String signatureName;

    /**
     * Signature Reason
     */
    @Value("${certificateGenerator.signature.reason}")
    private String signatureReason;

    /**
     * Signature Contact Info
     */
    @Value("${certificateGenerator.signature.contact-info}")
    private String signatureContactInfo;

    @PostConstruct
    protected void onPostConstruct() {
        log.debug("baseFolder: " + baseFolder);
        log.debug("backgroundInnovate: " + backgroundInnovate);
        log.debug("backgroundProfessional: " + backgroundProfessional);
        log.debug("backgroundStandard: " + backgroundStandard);
        log.debug("templateFile: " + templateFile);
        log.debug("timestampAuthority: " + timestampAuthority);
        log.debug("caNamePlaceholder: " + caNamePlaceholder);
        log.debug("studentNamePlaceholder: " + studentNamePlaceholder);
        log.debug("courseNamePlaceholder: " + courseNamePlaceholder);
        log.debug("studentQualificationPlaceholder: " + studentQualificationPlaceholder);
        log.debug("certificateDatePlaceholder: " + certificateDatePlaceholder);
        log.debug("keystorePath: " + keystoreFile);
        log.debug("keystorePassword: " + keystorePassword);
        log.debug("keystoreCertificateAlias: " + keystoreCertificateAlias);
        log.debug("keystoreType: " + keystoreType);
        log.debug("signatureName: " + signatureName);
        log.debug("signatureReason: " + signatureReason);
        log.debug("signatureContactInfo: " + signatureContactInfo);
    }

}
