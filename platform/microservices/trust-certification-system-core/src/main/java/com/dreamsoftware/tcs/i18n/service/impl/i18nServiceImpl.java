package com.dreamsoftware.tcs.i18n.service.impl;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.MissingResourceException;

/**
 *
 * @author ssanchez
 */
@Service("i18nService")
public class i18nServiceImpl implements I18NService {

    private static final Logger logger = LoggerFactory.getLogger(i18nServiceImpl.class);

    private List<Locale> localeSupported = new ArrayList<>();

    /**
     * Configure Default Locale
     *
     * @param defaultLocale
     */
    @Value("${i18n.default.locale}")
    public void setDefaultLocale(String defaultLocale) {
        if (defaultLocale != null && !defaultLocale.isEmpty()) {
            final Locale localeConfig = parseLocale(defaultLocale);
            if (isValid(localeConfig)) {
                logger.debug("Default Locale -> " + localeConfig.toString());
                Locale.setDefault(localeConfig);
            } else {
                logger.debug("Invalid default locale ");
            }
        }
    }

    /**
     * Configure Support Locale
     *
     * @param localeSupportedConfig
     */
    @Value("${i18n.locale.supported}")
    public void setLocaleSupported(String localeSupportedConfig) {
        final String[] localeSupportedArr = localeSupportedConfig.split(",");
        for (String localeString : localeSupportedArr) {
            final Locale currentLocale = parseLocale(localeString);
            if (isValid(currentLocale)) {
                localeSupported.add(currentLocale);
            }
        }
    }

    @Override
    public Locale parseLocale(String locale) {
        String[] parts = locale.split("_");
        switch (parts.length) {
            case 3:
                return new Locale(parts[0], parts[1], parts[2]);
            case 2:
                return new Locale(parts[0], parts[1]);
            case 1:
                return new Locale(parts[0]);
            default:
                throw new IllegalArgumentException("Invalid locale: " + locale);
        }
    }

    @Override
    public boolean isValid(Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    @Override
    public boolean isValid(String locale) {
        boolean isLocaleValid;
        try {
            isLocaleValid = isValid(parseLocale(locale));
        } catch (Exception ex) {
            isLocaleValid = false;
        }
        return isLocaleValid;
    }

    @Override
    public Locale parseLocaleOrDefault(String locale) {
        Locale userLocale = Locale.getDefault();
        logger.debug("Default Locale -> " + userLocale.toString());
        if (locale != null && !locale.isEmpty()) {
            final Locale localeParsed = parseLocale(locale);
            logger.debug("Locale Parsed -> " + localeParsed);
            if (isValid(localeParsed) && localeSupported.contains(localeParsed)) {
                logger.debug("Locale Parsed is valid");
                userLocale = localeParsed;
            }
        }
        return userLocale;
    }

    @Override
    public Locale getDefault() {
        return Locale.getDefault();
    }
}
