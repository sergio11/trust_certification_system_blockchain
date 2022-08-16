package com.dreamsoftware.tcs.config.properties;

import javax.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Data
@Component
@RefreshScope
public class CertificateGeneratorProperties {

    private final Logger logger = LoggerFactory.getLogger(MailProperties.class);

    /**
     * Watermark File
     */
    @Value("${certificateGenerator.watermarkFile}")
    private String watermarkFile;

    /**
     * Template File
     */
    @Value("${certificateGenerator.templateFile}")
    private String templateFile;

    /**
     * CA Name Placeholder
     */
    @Value("${certificateGenerator.placeholders.caName}")
    private String caNamePlaceholder;

    /**
     * Student Name Placeholder
     */
    @Value("${certificateGenerator.placeholders.studentName}")
    private String studentNamePlaceholder;

    /**
     * Course Name Placeholder
     */
    @Value("${certificateGenerator.placeholders.courseName}")
    private String courseNamePlaceholder;

    /**
     * Student Qualification Placeholder
     */
    @Value("${certificateGenerator.placeholders.studentQualification}")
    private String studentQualificationPlaceholder;

    /**
     * Certificate Date Placeholder
     */
    @Value("${certificateGenerator.placeholders.certificateDate}")
    private String certificateDatePlaceholder;

    /**
     *
     */
    @PostConstruct
    protected void onPostConstruct() {
        logger.debug("watermarkFile: " + watermarkFile);
        logger.debug("templateFile: " + templateFile);
        logger.debug("caNamePlaceholder: " + caNamePlaceholder);
        logger.debug("studentNamePlaceholder: " + studentNamePlaceholder);
        logger.debug("courseNamePlaceholder: " + courseNamePlaceholder);
        logger.debug("studentQualificationPlaceholder: " + studentQualificationPlaceholder);
        logger.debug("certificateDatePlaceholder: " + certificateDatePlaceholder);
    }

}
