package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.validation.constraints.CourseEditionMustAllowCheckIn;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CourseEditionMustAllowCheckInValidator implements ConstraintValidator<CourseEditionMustAllowCheckIn, String> {

    @Autowired
    private ICertificationCourseService certificationCourseService;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return certificationCourseService.courseEditionAllowCheckIn(id);
    }
}
