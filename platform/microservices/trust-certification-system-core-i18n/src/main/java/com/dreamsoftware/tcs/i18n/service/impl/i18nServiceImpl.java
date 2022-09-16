package com.dreamsoftware.tcs.i18n.service.impl;

import com.dreamsoftware.tcs.i18n.config.properties.i18nProperties;
import com.dreamsoftware.tcs.i18n.service.I18NService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import java.util.MissingResourceException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service("i18nService")
@RequiredArgsConstructor
public class i18nServiceImpl implements I18NService {

    private final i18nProperties i18nProperties;

    private List<Locale> localeSupported = new ArrayList<>();

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
        log.debug("Default Locale -> " + userLocale.toString());
        if (locale != null && !locale.isEmpty()) {
            final Locale localeParsed = parseLocale(locale);
            log.debug("Locale Parsed -> " + localeParsed);
            if (isValid(localeParsed) && localeSupported.contains(localeParsed)) {
                log.debug("Locale Parsed is valid");
                userLocale = localeParsed;
            }
        }
        return userLocale;
    }

    @Override
    public Locale getDefault() {
        return Locale.getDefault();
    }

    /**
     * Configure Default Locale
     *
     * @param defaultLocale
     */
    private void setDefaultLocale(String defaultLocale) {
        if (defaultLocale != null && !defaultLocale.isEmpty()) {
            final Locale localeConfig = parseLocale(defaultLocale);
            if (isValid(localeConfig)) {
                log.debug("Default Locale -> " + localeConfig.toString());
                Locale.setDefault(localeConfig);
            } else {
                log.debug("Invalid default locale ");
            }
        }
    }

    /**
     * Configure Support Locale
     *
     * @param localeSupportedConfig
     */
    private void setLocaleSupported(String localeSupportedConfig) {
        final String[] localeSupportedArr = localeSupportedConfig.split(",");
        for (String localeString : localeSupportedArr) {
            final Locale currentLocale = parseLocale(localeString);
            if (isValid(currentLocale)) {
                localeSupported.add(currentLocale);
            }
        }
    }

    @PostConstruct
    private void onPostConstruct() {
        setDefaultLocale(i18nProperties.getDefaultLocale());
        setLocaleSupported(i18nProperties.getLocaleSupported());
    }
}
