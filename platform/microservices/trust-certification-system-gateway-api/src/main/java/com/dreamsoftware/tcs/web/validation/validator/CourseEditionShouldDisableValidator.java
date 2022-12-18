package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CourseEditionShouldDisable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CourseEditionShouldDisableValidator implements ConstraintValidator<CourseEditionShouldDisable, String> {

    @Autowired
    private CertificationCourseEditionRepository certificationCourseEditionRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && certificationCourseEditionRepository.countByIdAndStatus(new ObjectId(id), CertificationCourseStateEnum.DISABLED) > 0;
    }
}
