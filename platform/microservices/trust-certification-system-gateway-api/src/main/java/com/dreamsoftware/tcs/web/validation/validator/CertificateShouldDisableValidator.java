package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.web.validation.constraints.CourseShouldDisable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CertificateShouldDisableValidator implements ConstraintValidator<CourseShouldDisable, String> {

    @Autowired
    private ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        try {
            return trustCertificationBlockchainRepository.isCertificateDisabled(id);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
