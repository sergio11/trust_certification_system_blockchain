package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldHaveAssociatedCertificate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class ShouldHaveAssociatedCertificateValidator implements ConstraintValidator<ShouldHaveAssociatedCertificate, String> {

    @Autowired
    private IAccountsService accountsService;

    @Autowired
    private CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;

    @Override
    public boolean isValid(String courseEditionId, ConstraintValidatorContext context) {
        final ICommonUserDetailsAware<String> principal = accountsService.getCurrentPrincipal();
        return principal != null && ObjectId.isValid(courseEditionId)
                && certificateIssuanceRequestRepository.countByStudentAndCourseEdition(new ObjectId(principal.getUserId()), new ObjectId(courseEditionId)) > 0;
    }
}
