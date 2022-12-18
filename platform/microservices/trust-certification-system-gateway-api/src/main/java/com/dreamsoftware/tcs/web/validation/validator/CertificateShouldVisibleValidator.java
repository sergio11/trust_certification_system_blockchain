package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldVisible;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CertificateShouldVisibleValidator implements ConstraintValidator<CertificateShouldVisible, String> {

    @Autowired
    private ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        try {
            return trustCertificationBlockchainRepository.isCertificateVisible(id);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
