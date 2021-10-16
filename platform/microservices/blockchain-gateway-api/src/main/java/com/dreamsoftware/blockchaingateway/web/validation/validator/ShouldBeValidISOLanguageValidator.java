package com.dreamsoftware.blockchaingateway.web.validation.validator;

import com.dreamsoftware.blockchaingateway.i18n.service.I18NService;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ShouldBeValidISOLanguage;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validate ISO3 Language
 *
 * @author ssanchez
 */
public class ShouldBeValidISOLanguageValidator implements ConstraintValidator<ShouldBeValidISOLanguage, String> {

    @Autowired
    private I18NService i18nService;

    @Override
    public boolean isValid(String locale, ConstraintValidatorContext arg1) {
        boolean isValidLang = true;
        if (StringUtils.isNotEmpty(locale)) {
            isValidLang = i18nService.isValid(locale);
        }
        return isValidLang;
    }
}
