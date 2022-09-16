package com.dreamsoftware.tcs.i18n.source;

import com.dreamsoftware.tcs.persistence.nosql.entity.TranslationLanguageEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.TranslationRepository;
import java.text.MessageFormat;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
public class DBMessageSource extends AbstractMessageSource {

    @Autowired
    private TranslationRepository translationRepository;

    @Override
    protected MessageFormat resolveCode(String key, Locale locale) {
        Assert.notNull(key, "Key can not be null");
        Assert.notNull(locale, "Locale can not be null");
        final String language = MessageFormat.format("{0}_{1}", locale.getLanguage(), locale.getCountry());
        logger.debug("Resolve code -> " + key + " for language " + language);
        final String message = translationRepository.findByNameAndLanguage(key,
                TranslationLanguageEnum.valueOf(language)).map(translationEntity
                -> translationEntity.getValue()).orElse(key);
        return new MessageFormat(message, locale);
    }
}
