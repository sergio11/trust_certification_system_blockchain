package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateTypeEnum;
import com.dreamsoftware.tcs.web.validation.constraints.ValidCertificateType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Certificate Type Validator
 *
 * @author ssanchez
 */
public class CertificateTypeValidator implements ConstraintValidator<ValidCertificateType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext cvc) {
        return EnumUtils.getEnum(CertificateTypeEnum.class, type) != null;
    }
}
