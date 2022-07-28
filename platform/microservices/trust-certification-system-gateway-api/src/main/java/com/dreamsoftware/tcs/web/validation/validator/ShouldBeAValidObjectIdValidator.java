package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeAValidObjectId;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public class ShouldBeAValidObjectIdValidator implements ConstraintValidator<ShouldBeAValidObjectId, String> {

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return ObjectId.isValid(id);
    }
}
