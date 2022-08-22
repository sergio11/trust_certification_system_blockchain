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
@EntityAnnotation(entityClass = CertificationCourseRegisteredMailRequestDTO.class)
public class CertificationCourseRegisteredMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private String courseId;

    /**
     * Course Name
     */
    private String courseName;

    /**
     *
     * @param courseId
     * @param courseName
     * @param email
     * @param locale
     */
    @Builder
    public CertificationCourseRegisteredMailRequestDTO(final String courseId, final String courseName, final String email, final Locale locale) {
        super(email, locale);
        this.courseId = courseId;
        this.courseName = courseName;
    }

    @Override
    public EmailTypeEnum getType() {
        return EmailTypeEnum.CERTIFICATION_COURSE_REGISTERED;
    }

}
