package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CourseShouldDisable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CourseShouldDisableValidator implements ConstraintValidator<CourseShouldDisable, String> {

    @Autowired
    private CertificationCourseRepository certificationCourseRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && certificationCourseRepository.countByIdAndStatus(new ObjectId(id), CertificationCourseStateEnum.DISABLED) > 0;
    }
}
