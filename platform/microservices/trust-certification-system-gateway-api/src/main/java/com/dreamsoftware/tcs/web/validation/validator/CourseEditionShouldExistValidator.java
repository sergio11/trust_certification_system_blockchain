package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CourseEditionShouldExist;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CourseEditionShouldExistValidator implements ConstraintValidator<CourseEditionShouldExist, String> {

    @Autowired
    private CertificationCourseEditionRepository certificationCourseEditionRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && certificationCourseEditionRepository.countById(new ObjectId(id)) > 0;
    }
}
