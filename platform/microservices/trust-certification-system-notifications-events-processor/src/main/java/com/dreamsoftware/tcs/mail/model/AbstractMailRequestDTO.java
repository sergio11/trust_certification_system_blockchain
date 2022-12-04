package com.dreamsoftware.tcs.mail.model;

import com.dreamsoftware.tcs.mail.model.ca.*;
import com.dreamsoftware.tcs.mail.model.certificate.*;
import com.dreamsoftware.tcs.mail.model.course.*;
import com.dreamsoftware.tcs.mail.model.user.ResetPasswordMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.user.UserActivatedEventMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.user.UserOrderCompletedMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.user.UserPendingValidationMailRequestDTO;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author ssanchez
 */
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.NAME)
@JsonSubTypes({
        @Type(CertificationAuthorityDisabledMailRequestDTO.class),
        @Type(CertificationAuthorityEnabledMailRequestDTO.class),
        @Type(CertificationAuthorityMemberDisabledMailRequestDTO.class),
        @Type(CertificationAuthorityMemberEnabledMailRequestDTO.class),
        @Type(CertificationAuthorityMemberRemovedMailRequestDTO.class),
        @Type(CertificationAuthorityRemovedMailRequestDTO.class),
        @Type(CertificateDisabledMailRequestDTO.class),
        @Type(CertificateEnabledMailRequestDTO.class),
        @Type(CertificateGeneratedMailRequestDTO.class),
        @Type(CertificateRenewedMailRequestDTO.class),
        @Type(CertificateRequestAcceptedMailRequestDTO.class),
        @Type(CertificateRequestRejectedMailRequestDTO.class),
        @Type(CertificateVisibilityChangedMailRequestDTO.class),
        @Type(IssueCertificateRequestMailRequestDTO.class),
        @Type(CourseDeletedMailRequestDTO.class),
        @Type(CourseDisabledMailRequestDTO.class),
        @Type(CourseEnabledMailRequestDTO.class),
        @Type(CourseEditionDeletedMailRequestDTO.class),
        @Type(CourseEditionDisabledMailRequestDTO.class),
        @Type(CourseEditionEnabledMailRequestDTO.class),
        @Type(CourseEditionRegisteredMailRequestDTO.class),
        @Type(CourseEditionUpdatedMailRequestDTO.class),
        @Type(CourseEditionEnrolledMailRequestDTO.class),
        @Type(UserActivatedEventMailRequestDTO.class),
        @Type(UserPendingValidationMailRequestDTO.class),
        @Type(ResetPasswordMailRequestDTO.class),
        @Type(UserOrderCompletedMailRequestDTO.class)
})
public abstract class AbstractMailRequestDTO {

    /**
     * Email
     */
    private final String email;

    /**
     * Locale
     */
    private final Locale locale;

    /**
     * @return
     */
    public Class getEntityType() {
        final Class aClass = this.getClass();
        final EntityAnnotation ne = AnnotationUtils.findAnnotation(aClass, EntityAnnotation.class);
        Class entityClass = null;
        if (ne != null) {
            entityClass = ne.entityClass();
        }
        return entityClass;
    }

}
