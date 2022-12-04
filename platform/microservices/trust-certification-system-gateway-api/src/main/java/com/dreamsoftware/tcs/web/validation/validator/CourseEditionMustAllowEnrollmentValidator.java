package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseAttendeeControlEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CourseEditionMustAllowEnrollment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author ssanchez
 */
public class CourseEditionMustAllowEnrollmentValidator implements ConstraintValidator<CourseEditionMustAllowEnrollment, String> {

    @Autowired
    private CertificationCourseEditionRepository certificationCourseEditionRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        AtomicBoolean isValid = new AtomicBoolean(false);
        if(ObjectId.isValid(id)) {
            certificationCourseEditionRepository.findById(new ObjectId(id)).ifPresent(certificationCourseEditionEntity -> {
                final CertificationCourseAttendeeControlEntity attendeeControlEntity = certificationCourseEditionEntity.getAttendeeControl();
                isValid.set(attendeeControlEntity != null && (attendeeControlEntity.getMaxAttendanceCount() == 0 || attendeeControlEntity.getMaxAttendanceCount() > 0 &&
                        attendeeControlEntity.getAttendedUsers().size() < attendeeControlEntity.getMaxAttendanceCount()));
            });
        }
        return isValid.get();
    }
}
