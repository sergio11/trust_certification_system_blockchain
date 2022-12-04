package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseAttendeeControlEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionAttendeeEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.UserMustNotYetBeEnrolled;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 *
 * @author ssanchez
 */
public class UserMustNotYetBeEnrolledValidator implements ConstraintValidator<UserMustNotYetBeEnrolled, String> {

    @Autowired
    private IAccountsService accountsService;

    @Autowired
    private CertificationCourseEditionRepository certificationCourseEditionRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        AtomicBoolean isValid = new AtomicBoolean(false);
        final ICommonUserDetailsAware<String> principal = accountsService.getCurrentPrincipal();
        if(principal != null && ObjectId.isValid(id)) {
            certificationCourseEditionRepository.findById(new ObjectId(id)).ifPresent(certificationCourseEditionEntity -> {
                final CertificationCourseAttendeeControlEntity attendeeControlEntity = certificationCourseEditionEntity.getAttendeeControl();
                if(attendeeControlEntity != null) {
                    final List<String> attendeeWalletHash = attendeeControlEntity.getAttendedUsers().stream()
                            .map(CertificationCourseEditionAttendeeEntity::getStudent)
                            .map(UserEntity::getWalletHash)
                            .collect(Collectors.toList());
                    isValid.set(!attendeeWalletHash.contains(principal.getWalletHash()));
                }
            });
        }
        return isValid.get();
    }
}
