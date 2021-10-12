package com.dreamsoftware.blockchaingateway.web.controller.translations;

import com.dreamsoftware.blockchaingateway.web.core.IResponseCodeTypes;

/**
 * Translations Response Code Enum Code Response interval -> 4XX
 *
 * @author ssanchez
 */
public enum TranslationsResponseCodeEnum implements IResponseCodeTypes {

    GET_TRANSLATIONS(400L),
    NO_TRANSLATIONS_FOUND(401L),
    SAVE_TRANSLATION(402L),
    SAVE_TRANSLATION_FAIL(403L),
    SAVE_ALL_TRANSLATION(404L),
    SAVE_ALL_TRANSLATION_FAIL(405L),
    TRANSLATION_NOT_FOUND(406L),
    PARTIAL_UPDATE_TRANSLATION(407L),
    PARTIAL_UPDATE_TRANSLATION_FAIL(408L),
    TRANSLATION_FILE_UPLOAD_SUCCESS(410L),
    TRANSLATION_FILE_UPLOAD_FAIL(411L);

    private final Long code;

    public static final String CATEGORY_NAME = "TRANSLATIONS";

    private TranslationsResponseCodeEnum(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

    @Override
    public String getCategoryName() {
        return CATEGORY_NAME;
    }

    @Override
    public String getCodeName() {
        return name();
    }

}
