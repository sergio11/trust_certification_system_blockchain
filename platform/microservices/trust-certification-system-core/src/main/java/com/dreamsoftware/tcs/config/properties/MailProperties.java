package com.dreamsoftware.tcs.config.properties;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Mail Properties
 *
 * @author sergio
 */
@Data
@Component
@RefreshScope
public class MailProperties implements Serializable {

    private Logger logger = LoggerFactory.getLogger(MailProperties.class);

    private static final long serialVersionUID = 1L;

    @Value("${mail.from}")
    private String mailFrom;

    @Value("${mail.templates.base.dir}")
    private String mailTemplatesBaseDir;

    @Value("${mail.resources.base.dir}")
    private String mailResourcesBaseDir;

    @Value("${mail.templates.accounts.user-activated}")
    private String userActivatedMailTemplate;

    @Value("${mail.templates.accounts.pending-validation}")
    private String userPendingValidationMailTemplate;

    @Value("${mail.templates.certificates.ca-disabled}")
    private String caDisabledMailTemplate;

    @Value("${mail.templates.certificates.ca-enabled}")
    private String caEnabledMailTemplate;

    @Value("${mail.templates.certificates.disabled}")
    private String certificateDisabledMailTemplate;

    @Value("${mail.templates.certificates.enabled}")
    private String certificateEnabledMailTemplate;

    @Value("${mail.templates.certificates.renewed}")
    private String certificateRenewedMailTemplate;

    @Value("${mail.templates.certificates.request-accepted}")
    private String certificateRequestAcceptedMailTemplate;

    @Value("${mail.templates.certificates.request-rejected}")
    private String certificateRequestRejectedMailTemplate;

    @Value("${mail.templates.certificates.visibility-changed}")
    private String certificateVisibilityChangedMailTemplate;

    @Value("${mail.templates.certificates.course-disabled}")
    private String courseDisabledMailTemplate;

    @Value("${mail.templates.certificates.course-enabled}")
    private String courseEnabledMailTemplate;

    @Value("${mail.templates.certificates.issue-certificate-request}")
    private String issueCertificateRequestMailTemplate;

    @Value("${mail.templates.certificates.new-course-registration-request}")
    private String newCourseRegistrationRequestedMailTemplate;

    @PostConstruct
    protected void onPostConstruct() {
        logger.debug("mailFrom: " + mailFrom);
        logger.debug("mailTemplatesBaseDir: " + mailTemplatesBaseDir);
        logger.debug("mailResourcesBaseDir: " + mailResourcesBaseDir);
        logger.debug("useActivatedMailTemplate: " + userActivatedMailTemplate);
        logger.debug("userPendingValidationMailTemplate: " + userPendingValidationMailTemplate);
        logger.debug("caDisabledMailTemplate: " + caDisabledMailTemplate);
        logger.debug("caEnabledMailTemplate: " + caEnabledMailTemplate);
        logger.debug("certificateDisabledMailTemplate: " + certificateDisabledMailTemplate);
        logger.debug("certificateEnabledMailTemplate: " + certificateEnabledMailTemplate);
        logger.debug("certificateRenewedMailTemplate: " + certificateRenewedMailTemplate);
        logger.debug("certificateRequestAcceptedMailTemplate: " + certificateRequestAcceptedMailTemplate);
        logger.debug("certificateRequestRejectedMailTemplate: " + certificateRequestRejectedMailTemplate);
        logger.debug("certificateVisibilityChangedMailTemplate: " + certificateVisibilityChangedMailTemplate);
        logger.debug("courseDisabledMailTemplate: " + courseDisabledMailTemplate);
        logger.debug("courseEnabledMailTemplate: " + courseEnabledMailTemplate);
        logger.debug("issueCertificateRequestMailTemplate: " + issueCertificateRequestMailTemplate);
        logger.debug("newCourseRegistrationRequestedMailTemplate: " + newCourseRegistrationRequestedMailTemplate);
    }
}
