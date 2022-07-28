package com.dreamsoftware.tcs.web.validation.validator;

import com.dreamsoftware.tcs.persistence.nosql.entity.TranslationLanguageEnum;
import com.dreamsoftware.tcs.web.validation.constraints.ValidTranslationLanguage;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

/**
 *
 * @author ssanchez
 */
public class ValidTranslationLanguageValidator implements ConstraintValidator<ValidTranslationLanguage, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext cvc) {
        return EnumUtils.getEnum(TranslationLanguageEnum.class, type) != null;
    }
}
