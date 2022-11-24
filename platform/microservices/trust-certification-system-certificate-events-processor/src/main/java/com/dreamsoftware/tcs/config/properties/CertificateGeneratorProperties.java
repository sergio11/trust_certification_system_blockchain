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
     * Background Achievement
     */
    @Value("${certificateGenerator.background.achievement}")
    private String backgroundAchievement;

    /**
     * Background Attendance
     */
    @Value("${certificateGenerator.background.attendance}")
    private String backgroundAttendance;

    /**
     * Achievement Certificate Template
     */
    @Value("${certificateGenerator.templates.achievement}")
    private String achievementCertificateTemplate;

    /**
     * Attendance Certificate Template
     */
    @Value("${certificateGenerator.templates.attendance}")
    private String attendanceCertificateTemplate;

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
     * Executive Director Placeholder
     */
    @Value("${certificateGenerator.placeholders.executive-director}")
    private String executiveDirectorPlaceholder;

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
        log.debug("backgroundAchievement: " + backgroundAchievement);
        log.debug("backgroundAttendance: " + backgroundAttendance);
        log.debug("templateFile: " + achievementCertificateTemplate);
        log.debug("timestampAuthority: " + timestampAuthority);
        log.debug("caNamePlaceholder: " + caNamePlaceholder);
        log.debug("executiveDirectorPlaceholder: " + executiveDirectorPlaceholder);
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
