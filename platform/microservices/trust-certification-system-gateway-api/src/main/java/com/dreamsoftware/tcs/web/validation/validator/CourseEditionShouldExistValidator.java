package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
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
        return !id.isBlank() && ObjectId.isValid(id) && certificationCourseEditionRepository.countByIdAndStatusNot(new ObjectId(id), CertificationCourseStateEnum.REMOVED) > 0;
    }
}
