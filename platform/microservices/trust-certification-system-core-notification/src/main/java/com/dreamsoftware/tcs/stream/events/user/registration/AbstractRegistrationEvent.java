package com.dreamsoftware.tcs.stream.events.user.registration;

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
        @JsonSubTypes.Type(OnNewCertificationAuthorityMemberRegistrationEvent.class),
        @JsonSubTypes.Type(OnNewCertificationAuthorityRegistrationEvent.class),
        @JsonSubTypes.Type(OnNewStudentRegistrationEvent.class)
})
public abstract class AbstractRegistrationEvent {

    /**
     * Get Entity Type
     *
     * @return
     */
    public Class<? extends AbstractRegistrationEvent> getEntityType() {
        final Class<? extends AbstractRegistrationEvent> aClass = this.getClass();
        final EntityAnnotation ne = AnnotationUtils.findAnnotation(aClass, EntityAnnotation.class);
        Class<? extends AbstractRegistrationEvent> entityClass = null;
        if (ne != null && ne.entityClass() != null) {
            entityClass = ne.entityClass();
        }
        return entityClass;
    }
}
