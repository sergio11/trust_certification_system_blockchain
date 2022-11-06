package com.dreamsoftware.tcs.mail.model.course;

import com.dreamsoftware.tcs.mail.model.AbstractMailRequestDTO;
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
@EntityAnnotation(entityClass = CourseEditionRegisteredMailRequestDTO.class)
public class CourseEditionRegisteredMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Course Id
     */
    private String courseId;

    /**
     * Edition Id
     */
    private String editionId;

    /**
     * Course Name
     */
    private String courseName;


    /**
     *
     * @param courseId
     * @param editionId
     * @param courseName
     * @param email
     * @param locale
     */
    @Builder
    public CourseEditionRegisteredMailRequestDTO(final String courseId, final String editionId, final String courseName, final String email, final Locale locale) {
        super(email, locale);
        this.courseId = courseId;
        this.editionId = editionId;
        this.courseName = courseName;
    }

}
