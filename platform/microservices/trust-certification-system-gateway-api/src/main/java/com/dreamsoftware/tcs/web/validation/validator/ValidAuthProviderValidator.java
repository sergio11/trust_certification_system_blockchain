package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderTypeEnum;
import com.dreamsoftware.tcs.web.validation.constraints.ValidAuthProviderType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

/**
 * Valid Auth Provider Validator
 *
 * @author ssanchez
 */
public class ValidAuthProviderValidator implements ConstraintValidator<ValidAuthProviderType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext cvc) {
        return EnumUtils.getEnum(AuthenticationProviderTypeEnum.class, type) != null;
    }
}
