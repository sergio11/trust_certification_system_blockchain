package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.validation.constraints.CourseEditionMustAllowEnrollment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CourseEditionMustAllowEnrollmentValidator implements ConstraintValidator<CourseEditionMustAllowEnrollment, String> {

    @Autowired
    private ICertificationCourseService certificationCourseService;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return !id.isBlank() && certificationCourseService.courseEditionAllowEnrollment(id);
    }
}
