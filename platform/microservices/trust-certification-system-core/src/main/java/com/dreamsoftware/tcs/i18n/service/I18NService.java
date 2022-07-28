package com.dreamsoftware.tcs.i18n.service;

import java.util.Locale;

/**
 *
 * @author ssanchez
 */
public interface I18NService {

    /**
     * Parse Locale
     *
     * @param locale
     * @return Locale
     */
    Locale parseLocale(String locale);

    /**
     * is valid
     *
     * @param locale
     * @return boolean
     */
    boolean isValid(Locale locale);

    /**
     * is valid
     *
     * @param locale
     * @return boolean
     */
    boolean isValid(String locale);

    /**
     * Get Default
     *
     * @return
     */
    Locale getDefault();

    /**
     * Parse Locale or default
     *
     * @param locale
     * @return Locale
     */
    Locale parseLocaleOrDefault(String locale);

}
