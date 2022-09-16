package com.dreamsoftware.tcs.i18n.service;

import java.util.Locale;

/**
 *
 * @author ssanchez
 */
public interface IMessageSourceResolverService {

    /**
     * Resolver
     *
     * @param key
     * @return
     */
    String resolver(String key);

    /**
     * Resolver
     *
     * @param key
     * @param locale
     * @return
     */
    String resolver(String key, Locale locale);

    /**
     * Resolver
     *
     * @param key
     * @param params
     * @return
     */
    String resolver(String key, Object[] params);

    /**
     * Resolver
     *
     * @param key
     * @param params
     * @param locale
     * @return
     */
    String resolver(String key, Object[] params, Locale locale);
}
