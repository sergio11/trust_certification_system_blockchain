package com.dreamsoftware.tcs.web.controller.translations;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Translations Response Code Enum Code Response interval -> 5XX
 *
 * @author ssanchez
 */
public enum TranslationsResponseCodeEnum implements IResponseCodeTypes {
    GET_TRANSLATIONS(500L),
    NO_TRANSLATIONS_FOUND(501L),
    SAVE_TRANSLATION(502L),
    SAVE_TRANSLATION_FAILED(503L),
    SAVE_ALL_TRANSLATION(504L),
    SAVE_ALL_TRANSLATION_FAILED(505L),
    TRANSLATION_NOT_FOUND(506L),
    PARTIAL_UPDATE_TRANSLATION(507L),
    PARTIAL_UPDATE_TRANSLATION_FAILED(508L),
    TRANSLATION_FILE_UPLOAD_SUCCESS(509L),
    TRANSLATION_FILE_UPLOAD_FAILED(510L);

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
