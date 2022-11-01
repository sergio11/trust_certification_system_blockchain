package com.dreamsoftware.tcs.stream.events.course;

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
        @JsonSubTypes.Type(CourseCertificateRegistrationRequestEvent.class),
        @JsonSubTypes.Type(DisableCertificationCourseEvent.class),
        @JsonSubTypes.Type(EnableCertificationCourseEvent.class),
        @JsonSubTypes.Type(RemoveCertificationCourseEvent.class),
        @JsonSubTypes.Type(RemoveCertificationCourseEditionEvent.class)
})
public abstract class AbstractCourseManagementEvent {

    /**
     * Get Entity Type
     *
     * @return
     */
    public Class<? extends AbstractCourseManagementEvent> getEntityType() {
        final Class<? extends AbstractCourseManagementEvent> aClass = this.getClass();
        final EntityAnnotation ne = AnnotationUtils.findAnnotation(aClass, EntityAnnotation.class);
        Class<? extends AbstractCourseManagementEvent> entityClass = null;
        if (ne != null && ne.entityClass() != null) {
            entityClass = ne.entityClass();
        }
        return entityClass;
    }
}
