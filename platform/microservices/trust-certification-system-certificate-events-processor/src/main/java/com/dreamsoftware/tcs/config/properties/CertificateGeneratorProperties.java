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
     * Watermark File
     */
    @Value("${certificateGenerator.watermark-file}")
    private String watermarkFile;

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
     *
     */
    @PostConstruct
    protected void onPostConstruct() {
        log.debug("watermarkFile: " + watermarkFile);
        log.debug("templateFile: " + templateFile);
        log.debug("timestampAuthority: " + timestampAuthority);
        log.debug("caNamePlaceholder: " + caNamePlaceholder);
        log.debug("studentNamePlaceholder: " + studentNamePlaceholder);
        log.debug("courseNamePlaceholder: " + courseNamePlaceholder);
        log.debug("studentQualificationPlaceholder: " + studentQualificationPlaceholder);
        log.debug("certificateDatePlaceholder: " + certificateDatePlaceholder);
    }

}
