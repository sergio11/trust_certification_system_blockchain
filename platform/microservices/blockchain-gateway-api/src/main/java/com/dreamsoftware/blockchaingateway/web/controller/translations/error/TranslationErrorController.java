package com.dreamsoftware.blockchaingateway.web.controller.translations.error;

import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.controller.translations.TranslationsResponseCodeEnum;
import com.dreamsoftware.blockchaingateway.web.controller.translations.error.exception.NoTranslationsFoundException;
import com.dreamsoftware.blockchaingateway.web.controller.translations.error.exception.SaveAllTranslationException;
import com.dreamsoftware.blockchaingateway.web.controller.translations.error.exception.SaveTranslationException;
import com.dreamsoftware.blockchaingateway.web.controller.translations.error.exception.TranslationNotFoundException;
import com.dreamsoftware.blockchaingateway.web.controller.translations.error.exception.UploadTranslationFileException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ssanchez
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TranslationErrorController extends SupportController {

    private static Logger logger = LoggerFactory.getLogger(TranslationErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SaveTranslationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSaveTranslationException(SaveTranslationException ex, HttpServletRequest request) {
        logger.error("Handler for SaveTranslationException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.SAVE_TRANSLATION_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, messageSourceResolver.resolver("save_translation_fail",
                        localeResolver.resolveLocale(request)));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SaveAllTranslationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSaveAllTranslationException(SaveAllTranslationException ex, HttpServletRequest request) {
        logger.error("Handler for SaveAllTranslationException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.SAVE_ALL_TRANSLATION_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, messageSourceResolver.resolver("save_all_translation_fail",
                        localeResolver.resolveLocale(request)));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(NoTranslationsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleNoTranslationsFoundException(NoTranslationsFoundException ex, HttpServletRequest request) {
        logger.error("Handler for NoTranslationsFoundException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.NO_TRANSLATIONS_FOUND,
                HttpStatus.NOT_FOUND, messageSourceResolver.resolver("no_translations_found", localeResolver.resolveLocale(request)));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(TranslationNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleTranslationNotFoundException(TranslationNotFoundException ex, HttpServletRequest request) {
        logger.error("Handler for TranslationNotFoundException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.TRANSLATION_NOT_FOUND,
                HttpStatus.NOT_FOUND, messageSourceResolver.resolver("translation_not_found", localeResolver.resolveLocale(request)));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(UploadTranslationFileException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleUploadTranslationFileException(UploadTranslationFileException ex, HttpServletRequest request) {
        logger.error("Handler for UploadTranslationFileException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.TRANSLATION_FILE_UPLOAD_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, messageSourceResolver.resolver("translation_file_upload_fail", localeResolver.resolveLocale(request)));
    }
}
