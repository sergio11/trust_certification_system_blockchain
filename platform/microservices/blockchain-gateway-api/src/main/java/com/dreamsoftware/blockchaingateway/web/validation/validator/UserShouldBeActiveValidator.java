package com.dreamsoftware.blockchaingateway.web.validation.validator;

import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.UserShouldBeActive;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
public class UserShouldBeActiveValidator implements ConstraintValidator<UserShouldBeActive, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cvc) {
        return true;
    }

}
