package com.dreamsoftware.tcs.web.controller.translations.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.translations.TranslationsResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.NoTranslationsFoundException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.SaveAllTranslationException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.SaveTranslationException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.TranslationNotFoundException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.UploadTranslationFileException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
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

    private static final Logger logger = LoggerFactory.getLogger(TranslationErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SaveTranslationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSaveTranslationException(final SaveTranslationException ex, final HttpServletRequest request) {
        logger.error("Handler for SaveTranslationException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.SAVE_TRANSLATION_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("save_translation_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SaveAllTranslationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSaveAllTranslationException(final SaveAllTranslationException ex, final HttpServletRequest request) {
        logger.error("Handler for SaveAllTranslationException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.SAVE_ALL_TRANSLATION_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("save_all_translation_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(NoTranslationsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleNoTranslationsFoundException(final NoTranslationsFoundException ex, final HttpServletRequest request) {
        logger.error("Handler for NoTranslationsFoundException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.NO_TRANSLATIONS_FOUND,
                HttpStatus.NOT_FOUND, resolveString("no_translations_found", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(TranslationNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleTranslationNotFoundException(final TranslationNotFoundException ex, final HttpServletRequest request) {
        logger.error("Handler for TranslationNotFoundException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.TRANSLATION_NOT_FOUND,
                HttpStatus.NOT_FOUND, resolveString("translation_not_found", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(UploadTranslationFileException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleUploadTranslationFileException(final UploadTranslationFileException ex, final HttpServletRequest request) {
        logger.error("Handler for UploadTranslationFileException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TranslationsResponseCodeEnum.TRANSLATION_FILE_UPLOAD_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("translation_file_upload_fail", request));
    }
}
