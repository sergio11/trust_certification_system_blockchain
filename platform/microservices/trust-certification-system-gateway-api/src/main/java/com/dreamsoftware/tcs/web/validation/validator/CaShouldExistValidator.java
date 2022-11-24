package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationAuthorityRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CaShouldExist;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CaShouldExistValidator implements ConstraintValidator<CaShouldExist, String> {

    @Autowired
    private CertificationAuthorityRepository certificationAuthorityRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && certificationAuthorityRepository.countById(new ObjectId(id)) > 0;
    }
}
