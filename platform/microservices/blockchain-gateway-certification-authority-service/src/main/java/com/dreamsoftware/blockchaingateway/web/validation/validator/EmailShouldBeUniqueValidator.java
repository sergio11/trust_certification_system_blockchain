package com.dreamsoftware.blockchaingateway.web.validation.validator;

import com.dreamsoftware.blockchaingateway.web.validation.constraints.EmailShouldBeUnique;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class EmailShouldBeUniqueValidator implements ConstraintValidator<EmailShouldBeUnique, String> {

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return true;
    }
}
