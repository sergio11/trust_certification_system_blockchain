package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldHaveEnoughFundForRenewingCertificate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author ssanchez
 */
public class ShouldHaveEnoughFundForRenewingCertificateValidator implements ConstraintValidator<ShouldHaveEnoughFundForRenewingCertificate, String> {

    @Autowired
    private ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;
    @Autowired
    private IAccountsService accountsService;
    @Autowired
    private CertificationCourseEditionRepository certificationCourseEditionRepository;

    @Override
    public boolean isValid(String courseEditionId, ConstraintValidatorContext context) {
        final AtomicBoolean isValid = new AtomicBoolean(false);
        final ICommonUserDetailsAware<String> principal = accountsService.getCurrentPrincipal();
        if(principal != null) {
            certificationCourseEditionRepository.findById(new ObjectId(courseEditionId)).ifPresent((certificationCourseEditionEntity -> {
                try {
                    isValid.set(tokenManagementBlockchainRepository.getMyTokens(principal.getWalletHash()) >= certificationCourseEditionEntity.getCostOfRenewingCertificate());
                } catch (RepositoryException e) {
                    isValid.set(false);
                }
            }));
        }
        return isValid.get();
    }
}
