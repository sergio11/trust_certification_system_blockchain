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
@EntityAnnotation(entityClass = CourseEnabledMailRequestDTO.class)
public class CourseEnabledMailRequestDTO extends AbstractMailRequestDTO {

    /**
     * Id
     */
    private final String id;

    /**
     * Course Name
     */
    private final String courseName;

    /**
     * CA Name
     */
    private final String caName;

    /**
     *
     * @param id
     * @param courseName
     * @param caName
     * @param email
     * @param locale
     */
    @Builder
    public CourseEnabledMailRequestDTO(final String id, final String courseName, final String caName, final String email, final Locale locale) {
        super(email, locale);
        this.id = id;
        this.courseName = courseName;
        this.caName = caName;
    }
}
