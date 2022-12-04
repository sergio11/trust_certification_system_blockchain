package com.dreamsoftware.tcs.mail.model.course;

import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Locale;

/**
 *
 * @author ssanchez
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@EntityAnnotation(entityClass = CourseEditionEnrolledMailRequestDTO.class)
public class CourseEditionEnrolledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String id;

    /**
     * Course Name
     */
    private final String courseName;

    /**
     * Student Name
     */
    private final String studentName;

    /**
     *
     * @param id
     * @param courseName
     * @param caName
     * @param email
     * @param locale
     */
    @Builder
    public CourseEditionEnrolledMailRequestDTO(final String id, final String courseName, final String studentName, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.courseName = courseName;
        this.studentName = studentName;
    }
}
