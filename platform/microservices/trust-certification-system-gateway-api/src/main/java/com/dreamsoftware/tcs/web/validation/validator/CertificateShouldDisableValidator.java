package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldDisable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
@Slf4j
public class CertificateShouldDisableValidator implements ConstraintValidator<CertificateShouldDisable, String> {

    @Autowired
    private ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        try {
            log.debug("CertificateShouldDisableValidator CALLED!");
            return trustCertificationBlockchainRepository.isCertificateDisabled(id);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
