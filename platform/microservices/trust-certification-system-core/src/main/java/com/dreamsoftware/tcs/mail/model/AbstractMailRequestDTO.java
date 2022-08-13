package com.dreamsoftware.tcs.mail.model;

import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;

/**
 *
 * @author ssanchez
 */
@Getter
@AllArgsConstructor
public abstract class AbstractMailRequestDTO {

    /**
     * Email
     */
    private String email;

    /**
     * Locale
     */
    private Locale locale;

    /**
     * Get Type
     *
     * @return
     */
    public abstract EmailTypeEnum getType();

    /**
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
