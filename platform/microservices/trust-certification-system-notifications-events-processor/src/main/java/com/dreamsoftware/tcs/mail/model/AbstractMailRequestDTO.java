package com.dreamsoftware.tcs.mail.model;

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
        @Type(CertificateDisabledMailRequestDTO.class),
        @Type(CertificateEnabledMailRequestDTO.class),
        @Type(CertificateRenewedMailRequestDTO.class),
        @Type(CertificateRequestAcceptedMailRequestDTO.class),
        @Type(CertificateRequestRejectedMailRequestDTO.class),
        @Type(CertificateVisibilityChangedMailRequestDTO.class),
        @Type(CourseDisabledMailRequestDTO.class),
        @Type(CourseEnabledMailRequestDTO.class),
        @Type(IssueCertificateRequestMailRequestDTO.class),
        @Type(NewCourseRegistrationRequestedMailRequestDTO.class),
        @Type(UserActivatedEventMailRequestDTO.class),
        @Type(UserPendingValidationMailRequestDTO.class),
        @Type(CertificationAuthorityMemberRemovedMailRequestDTO.class),
        @Type(CertificationAuthorityRemovedMailRequestDTO.class)
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
