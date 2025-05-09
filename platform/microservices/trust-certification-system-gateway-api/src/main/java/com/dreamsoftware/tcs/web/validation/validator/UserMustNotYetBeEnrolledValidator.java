package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.validation.constraints.UserMustNotYetBeEnrolled;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class UserMustNotYetBeEnrolledValidator implements ConstraintValidator<UserMustNotYetBeEnrolled, String> {

    @Autowired
    private ICertificationCourseService certificationCourseService;

    @Override
    public boolean isValid(String courseEditionId, ConstraintValidatorContext context) {
        return !certificationCourseService.currentUserHasBeenEnrolledTo(courseEditionId);
    }
}
