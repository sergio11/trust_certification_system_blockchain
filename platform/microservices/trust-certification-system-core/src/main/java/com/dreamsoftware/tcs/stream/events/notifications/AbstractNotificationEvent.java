package com.dreamsoftware.tcs.stream.events.notifications;

import com.dreamsoftware.tcs.stream.events.notifications.users.UserPendingValidationNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CADisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CAEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateDisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateIssuedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRenewedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestAcceptedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestRejectedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateVisibilityChangedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.IssueCertificateRequestedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CertificationCourseRegisteredNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDeletedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.NewCourseRegistrationRequestedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.OrderCompletedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.PasswordResetNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;

/**
 *
 * @author ssanchez
 */
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(UserPendingValidationNotificationEvent.class),
    @JsonSubTypes.Type(PasswordResetNotificationEvent.class),
    @JsonSubTypes.Type(CAEnabledNotificationEvent.class),
    @JsonSubTypes.Type(CADisabledNotificationEvent.class),
    @JsonSubTypes.Type(CertificateDisabledNotificationEvent.class),
    @JsonSubTypes.Type(CertificateEnabledNotificationEvent.class),
    @JsonSubTypes.Type(CertificateRenewedNotificationEvent.class),
    @JsonSubTypes.Type(CertificateRequestAcceptedNotificationEvent.class),
    @JsonSubTypes.Type(CertificateRequestRejectedNotificationEvent.class),
    @JsonSubTypes.Type(CertificateVisibilityChangedNotificationEvent.class),
    @JsonSubTypes.Type(IssueCertificateRequestedNotificationEvent.class),
    @JsonSubTypes.Type(CourseDeletedNotificationEvent.class),
    @JsonSubTypes.Type(CourseDisabledNotificationEvent.class),
    @JsonSubTypes.Type(CourseEnabledNotificationEvent.class),
    @JsonSubTypes.Type(NewCourseRegistrationRequestedNotificationEvent.class),
    @JsonSubTypes.Type(OrderCompletedNotificationEvent.class),
    @JsonSubTypes.Type(UserRegisteredNotificationEvent.class),
    @JsonSubTypes.Type(CertificationCourseRegisteredNotificationEvent.class),
    @JsonSubTypes.Type(CertificateIssuedNotificationEvent.class)
})
public class AbstractNotificationEvent {

    /**
     * Get Entity Type
     *
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
