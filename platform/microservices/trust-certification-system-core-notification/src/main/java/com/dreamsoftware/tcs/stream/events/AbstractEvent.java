package com.dreamsoftware.tcs.stream.events;

import com.dreamsoftware.tcs.stream.events.certificate.*;
import com.dreamsoftware.tcs.stream.events.course.*;
import com.dreamsoftware.tcs.stream.events.notifications.ca.*;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.*;
import com.dreamsoftware.tcs.stream.events.notifications.course.*;
import com.dreamsoftware.tcs.stream.events.notifications.users.OrderCompletedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserPasswordResetNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserPendingValidationNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.*;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(OnNewIssueCertificateRequestEvent.class),
        @JsonSubTypes.Type(EnableCertificateRequestEvent.class),
        @JsonSubTypes.Type(DisableCertificateRequestEvent.class),
        @JsonSubTypes.Type(UpdateCertificateVisibilityRequestEvent.class),
        @JsonSubTypes.Type(RenewCertificateRequestEvent.class),
        @JsonSubTypes.Type(NewCertificationCourseEditionRegistrationRequestEvent.class),
        @JsonSubTypes.Type(UpdateCertificationCourseEditionRequestEvent.class),
        @JsonSubTypes.Type(DisableCertificationCourseEvent.class),
        @JsonSubTypes.Type(DisableCertificationCourseEditionEvent.class),
        @JsonSubTypes.Type(EnableCertificationCourseEvent.class),
        @JsonSubTypes.Type(EnableCertificationCourseEditionEvent.class),
        @JsonSubTypes.Type(RemoveCertificationCourseEvent.class),
        @JsonSubTypes.Type(RemoveCertificationCourseEditionEvent.class),
        @JsonSubTypes.Type(UserPendingValidationNotificationEvent.class),
        @JsonSubTypes.Type(CertificateDisabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificateEnabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificateRenewedNotificationEvent.class),
        @JsonSubTypes.Type(CertificateRequestAcceptedNotificationEvent.class),
        @JsonSubTypes.Type(CertificateRequestRejectedNotificationEvent.class),
        @JsonSubTypes.Type(CertificateVisibilityChangedNotificationEvent.class),
        @JsonSubTypes.Type(IssueCertificateRequestedNotificationEvent.class),
        @JsonSubTypes.Type(CourseDeletedNotificationEvent.class),
        @JsonSubTypes.Type(CourseEditionDeletedNotificationEvent.class),
        @JsonSubTypes.Type(CourseDisabledNotificationEvent.class),
        @JsonSubTypes.Type(CourseEditionDisabledNotificationEvent.class),
        @JsonSubTypes.Type(CourseEnabledNotificationEvent.class),
        @JsonSubTypes.Type(CourseEditionEnabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificationCourseEditionUpdatedNotificationEvent.class),
        @JsonSubTypes.Type(OrderCompletedNotificationEvent.class),
        @JsonSubTypes.Type(UserRegisteredNotificationEvent.class),
        @JsonSubTypes.Type(CourseEditionRegisteredNotificationEvent.class),
        @JsonSubTypes.Type(CertificateIssuedNotificationEvent.class),
        @JsonSubTypes.Type(CertificationAuthorityDisabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificationAuthorityEnabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificationAuthorityMemberDisabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificationAuthorityMemberEnabledNotificationEvent.class),
        @JsonSubTypes.Type(CertificationAuthorityMemberRemovedNotificationEvent.class),
        @JsonSubTypes.Type(CertificationAuthorityRemovedNotificationEvent.class),
        @JsonSubTypes.Type(UserPasswordResetNotificationEvent.class),
        @JsonSubTypes.Type(NewCertificationAuthorityMemberEvent.class),
        @JsonSubTypes.Type(NewCertificationAuthorityEvent.class),
        @JsonSubTypes.Type(NewStudentEvent.class),
        @JsonSubTypes.Type(DisableCertificationAuthorityEvent.class),
        @JsonSubTypes.Type(DisableCertificationAuthorityMemberEvent.class),
        @JsonSubTypes.Type(EnableCertificationAuthorityEvent.class),
        @JsonSubTypes.Type(EnableCertificationAuthorityMemberEvent.class),
        @JsonSubTypes.Type(RemoveCertificationAuthorityEvent.class),
        @JsonSubTypes.Type(RemoveCertificationAuthorityMemberEvent.class),
        @JsonSubTypes.Type(UserPasswordResetEvent.class)
})
public abstract class AbstractEvent {

    public Class<? extends AbstractEvent> getEntityType() {
        final Class<? extends AbstractEvent> aClass = this.getClass();
        final EntityAnnotation ne = AnnotationUtils.findAnnotation(aClass, EntityAnnotation.class);
        Class<? extends AbstractEvent> entityClass = null;
        if (ne != null && ne.entityClass() != null) {
            entityClass = ne.entityClass();
        }
        return entityClass;
    }
}
