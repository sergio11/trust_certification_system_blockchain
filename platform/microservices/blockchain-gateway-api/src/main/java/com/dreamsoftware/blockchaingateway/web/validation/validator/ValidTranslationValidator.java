package com.dreamsoftware.blockchaingateway.web.validation.validator;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TranslationLanguageEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.TranslationRepository;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveTranslationDTO;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ValidTranslation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
public class ValidTranslationValidator implements ConstraintValidator<ValidTranslation, SaveTranslationDTO> {

    @Autowired
    private TranslationRepository translationRepository;

    @Override
    public boolean isValid(SaveTranslationDTO translation, ConstraintValidatorContext cvc) {
        return translationRepository.countByNameAndLanguage(translation.getName(),
                TranslationLanguageEnum.valueOf(translation.getLanguage())) == 0;
    }

}
