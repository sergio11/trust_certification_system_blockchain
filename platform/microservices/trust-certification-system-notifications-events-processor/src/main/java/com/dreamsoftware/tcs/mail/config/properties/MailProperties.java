package com.dreamsoftware.tcs.mail.config.properties;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Mail Properties
 *
 * @author sergio
 */
@Slf4j
@Data
@Component
@RefreshScope
public class MailProperties implements Serializable {

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

    @Value("${mail.templates.accounts.reset-password}")
    private String userResetPassword;

    @Value("${mail.templates.accounts.user-order-completed}")
    private String userOrderCompletedMailTemplate;

    @Value("${mail.templates.certificates.ca-disabled}")
    private String caDisabledMailTemplate;

    @Value("${mail.templates.certificates.ca-enabled}")
    private String caEnabledMailTemplate;

    @Value("${mail.templates.certificates.ca-member-disabled}")
    private String caMemberDisabledMailTemplate;

    @Value("${mail.templates.certificates.ca-member-enabled}")
    private String caMemberEnabledMailTemplate;

    @Value("${mail.templates.certificates.ca-member-removed}")
    private String caMemberRemovedMailTemplate;

    @Value("${mail.templates.certificates.ca-removed}")
    private String caRemovedMailTemplate;

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

    @Value("${mail.templates.certificates.course-deleted}")
    private String courseDeletedMailTemplate;

    @Value("${mail.templates.certificates.course-edition-disabled}")
    private String courseEditionDisabledMailTemplate;

    @Value("${mail.templates.certificates.course-edition-enabled}")
    private String courseEditionEnabledMailTemplate;

    @Value("${mail.templates.certificates.course-edition-deleted}")
    private String courseEditionDeletedMailTemplate;

    @Value("${mail.templates.certificates.course-edition-updated}")
    private String courseEditionUpdatedMailTemplate;

    @Value("${mail.templates.certificates.issue-certificate-request}")
    private String issueCertificateRequestMailTemplate;

    @Value("${mail.templates.certificates.certificate-generated}")
    private String certificateGeneratedMailTemplate;

    @Value("${mail.templates.certificates.certification-course-edition-registered}")
    private String certificationCourseEditionRegisteredMailTemplate;

    @PostConstruct
    protected void onPostConstruct() {
        log.debug("mailFrom: " + mailFrom);
        log.debug("mailTemplatesBaseDir: " + mailTemplatesBaseDir);
        log.debug("mailResourcesBaseDir: " + mailResourcesBaseDir);
        log.debug("useActivatedMailTemplate: " + userActivatedMailTemplate);
        log.debug("userPendingValidationMailTemplate: " + userPendingValidationMailTemplate);
        log.debug("caDisabledMailTemplate: " + caDisabledMailTemplate);
        log.debug("caEnabledMailTemplate: " + caEnabledMailTemplate);
        log.debug("caMemberDisabledMailTemplate: " + caMemberDisabledMailTemplate);
        log.debug("caMemberEnabledMailTemplate: " + caMemberEnabledMailTemplate);
        log.debug("caMemberRemovedMailTemplate: " + caMemberRemovedMailTemplate);
        log.debug("caRemovedMailTemplate: " + caRemovedMailTemplate);
        log.debug("certificateDisabledMailTemplate: " + certificateDisabledMailTemplate);
        log.debug("certificateEnabledMailTemplate: " + certificateEnabledMailTemplate);
        log.debug("certificateRenewedMailTemplate: " + certificateRenewedMailTemplate);
        log.debug("certificateRequestAcceptedMailTemplate: " + certificateRequestAcceptedMailTemplate);
        log.debug("certificateRequestRejectedMailTemplate: " + certificateRequestRejectedMailTemplate);
        log.debug("certificateVisibilityChangedMailTemplate: " + certificateVisibilityChangedMailTemplate);
        log.debug("courseDisabledMailTemplate: " + courseDisabledMailTemplate);
        log.debug("courseEnabledMailTemplate: " + courseEnabledMailTemplate);
        log.debug("courseDeletedMailTemplate: " + courseDeletedMailTemplate);
        log.debug("courseEditionDisabledMailTemplate: " + courseEditionDisabledMailTemplate);
        log.debug("courseEditionEnabledMailTemplate: " + courseEditionEnabledMailTemplate);
        log.debug("courseEditionDeletedMailTemplate: " + courseEditionDeletedMailTemplate);
        log.debug("courseEditionUpdatedMailTemplate: " + courseEditionUpdatedMailTemplate);
        log.debug("issueCertificateRequestMailTemplate: " + issueCertificateRequestMailTemplate);
        log.debug("certificationCourseRegisteredMailTemplate: " + certificationCourseEditionRegisteredMailTemplate);
        log.debug("certificateGeneratedMailTemplate: " + certificateGeneratedMailTemplate);
        log.debug("userOrderCompletedMailTemplate: " + userOrderCompletedMailTemplate);
        log.debug("userResetPassword: " + userResetPassword);
    }
}
