package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.web.validation.constraints.UserWithEmailShouldExists;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public final class UserWithEmailShouldExistsValidator implements ConstraintValidator<UserWithEmailShouldExists, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserWithEmailShouldExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext arg1) {
        return Boolean.TRUE;
    }

}
