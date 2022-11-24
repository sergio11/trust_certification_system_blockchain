package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.web.validation.constraints.CaMemberShouldExist;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ssanchez
 */
public class CaMemberShouldExistValidator implements ConstraintValidator<CaMemberShouldExist, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id) && userRepository.countByIdAndCaIsNotNull(new ObjectId(id)) > 0;
    }
}
