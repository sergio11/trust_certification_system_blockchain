package com.dreamsoftware.tcs.stream.events.certificate;

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
        @JsonSubTypes.Type(RenewCertificateRequestEvent.class)
})
public abstract class AbstractCertificateManagementEvent {

    /**
     * Get Entity Type
     *
     * @return
     */
    public Class<? extends AbstractCertificateManagementEvent> getEntityType() {
        final Class<? extends AbstractCertificateManagementEvent> aClass = this.getClass();
        final EntityAnnotation ne = AnnotationUtils.findAnnotation(aClass, EntityAnnotation.class);
        Class<? extends AbstractCertificateManagementEvent> entityClass = null;
        if (ne != null && ne.entityClass() != null) {
            entityClass = ne.entityClass();
        }
        return entityClass;
    }
}
