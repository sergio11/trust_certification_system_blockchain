package com.dreamsoftware.tcs.stream.events.user;

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
public abstract class AbstractUserManagementEvent {

    /**
     * Get Entity Type
     *
     * @return
     */
    public Class<? extends AbstractUserManagementEvent> getEntityType() {
        final Class<? extends AbstractUserManagementEvent> aClass = this.getClass();
        final EntityAnnotation ne = AnnotationUtils.findAnnotation(aClass, EntityAnnotation.class);
        Class<? extends AbstractUserManagementEvent> entityClass = null;
        if (ne != null && ne.entityClass() != null) {
            entityClass = ne.entityClass();
        }
        return entityClass;
    }
}
