package com.dreamsoftware.tcs.mail.model;

import com.dreamsoftware.tcs.persistence.nosql.entity.EmailTypeEnum;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@EntityAnnotation(entityClass = CourseDeletedMailRequestDTO.class)
public class CourseDeletedMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String id;

    /**
     * Course Name
     */
    private String courseName;

    /**
     * CA Name
     */
    private String caName;

    /**
     *
     * @param id
     * @param courseName
     * @param caName
     * @param email
     * @param locale
     */
    @Builder
    public CourseDeletedMailRequestDTO(final String id, final String courseName, final String caName, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.courseName = courseName;
        this.caName = caName;
    }

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.COURSE_ENABLED;
    }
}
