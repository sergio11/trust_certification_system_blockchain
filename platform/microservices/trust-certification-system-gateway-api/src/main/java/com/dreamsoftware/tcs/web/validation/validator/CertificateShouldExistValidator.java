package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldExist;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CertificateShouldExistValidator implements ConstraintValidator<CertificateShouldExist, String> {

    @Autowired
    private CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return certificateIssuanceRequestRepository.countByCertificateId(id) > 0;
    }
}
