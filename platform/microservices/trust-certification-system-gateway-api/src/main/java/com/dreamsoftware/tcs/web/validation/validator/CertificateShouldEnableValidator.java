package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldEnable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CertificateShouldEnableValidator implements ConstraintValidator<CertificateShouldEnable, String> {

    @Autowired
    private ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        try {
            return trustCertificationBlockchainRepository.isCertificateEnabled(id);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
