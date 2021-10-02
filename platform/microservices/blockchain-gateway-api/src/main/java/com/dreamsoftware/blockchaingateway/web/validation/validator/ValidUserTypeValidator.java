package com.dreamsoftware.blockchaingateway.web.validation.validator;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ValidUserType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

/**
 * Valid User Type Validator
 *
 * @author ssanchez
 */
public class ValidUserTypeValidator implements ConstraintValidator<ValidUserType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext cvc) {
        return EnumUtils.getEnum(UserTypeEnum.class, type) != null;
    }
}
