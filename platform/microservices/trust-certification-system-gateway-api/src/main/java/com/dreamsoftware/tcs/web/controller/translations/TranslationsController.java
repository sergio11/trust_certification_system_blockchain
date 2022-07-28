package com.dreamsoftware.tcs.web.controller.translations;

import com.dreamsoftware.tcs.services.ITranslationsService;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.NoTranslationsFoundException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.SaveAllTranslationException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.SaveTranslationException;
import com.dreamsoftware.tcs.web.controller.translations.error.exception.UploadTranslationFileException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveTranslationDTO;
import com.dreamsoftware.tcs.web.dto.response.TranslationDTO;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
import com.dreamsoftware.tcs.web.validation.constraints.ValidTranslationFile;
import com.dreamsoftware.tcs.web.validation.constraints.ValidTranslationLanguage;
import com.google.common.collect.Iterables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ssanchez
 */
@RestController
@Validated
@RequestMapping("/api/v1/translations/")
@Tag(name = "translations", description = "/api/v1/translations/ (Code Response interval -> 4XX)")
@RequiredArgsConstructor
public class TranslationsController extends SupportController {

    private static final Logger logger = LoggerFactory.getLogger(TranslationsController.class);

    private final ITranslationsService translationService;

    /**
     *
     * @param language
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_TRANSLATIONS - Get Translations by language", description = "Get Translations by language", tags = {"translations"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Translation List",
                content = @Content(schema = @Schema(implementation = TranslationDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Translations Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/{language}", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Iterable<TranslationDTO>>> getTranslations(
            @ValidTranslationLanguage(message = "{translation_language_invalid}")
            @PathVariable String language
    ) throws Throwable {

        final Iterable<TranslationDTO> translationList = translationService.findByLanguageOrderByNameAsc(language);

        if (Iterables.isEmpty(translationList)) {
            throw new NoTranslationsFoundException("No translation found", null);
        }

        return responseHelper.createAndSendResponse(
                TranslationsResponseCodeEnum.GET_TRANSLATIONS,
                HttpStatus.OK, translationList
        );
    }

    /**
     * Delete Translation By Id
     *
     * @param id
     * @return
     */
    @Operation(summary = "DELETE_TRANSLATION_BY_ID", description = "Delete Translation By Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<String>> delete(
            @Parameter(name = "id", description = "User id", required = true)
            @PathVariable Long id
    ) {

        return null;

    }

    /**
     *
     * @param translation
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SAVE_TRANSLATION - Save Translation (only access for root user)", description = "Save Translation", tags = {"translations"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Translation List",
                content = @Content(schema = @Schema(implementation = TranslationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Save translation fail exception",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<TranslationDTO>> save(
            @Parameter(name = "translation", description = "Translation Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SaveTranslationDTO.class))
            @Validated(ICommonSequence.class) SaveTranslationDTO translation) throws Throwable {

        try {
            return responseHelper.createAndSendResponse(
                    TranslationsResponseCodeEnum.SAVE_TRANSLATION, HttpStatus.OK,
                    translationService.save(translation));

        } catch (final Exception ex) {
            throw new SaveTranslationException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param translations
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SAVE_ALL_TRANSLATION", description = "Save All Translation")
    @RequestMapping(value = "/all", method = RequestMethod.PUT)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Iterable<TranslationDTO>>> save(
            @Parameter(name = "translations", description = "translations", required = true)
            @RequestBody
            @NotEmpty(message = "{translations_not_empty}") List<@Valid SaveTranslationDTO> translations) throws Throwable {

        try {
            return responseHelper.createAndSendResponse(
                    TranslationsResponseCodeEnum.SAVE_ALL_TRANSLATION,
                    HttpStatus.OK,
                    translationService.save(translations));

        } catch (final Exception ex) {
            throw new SaveAllTranslationException(ex.getMessage(), ex);
        }
    }

    /**
     * Upload Translation File
     *
     * @param multipartFile
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "UPLOAD_TRANSLATION_FILE", description = "Upload Translation XLS File")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<String>> uploadTranslationFile(
            @Valid @RequestPart("file") @ValidTranslationFile(message = "{ translation_file_invalid }") MultipartFile multipartFile,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {

        File translationTempFile = null;
        try {

            // ask JVM to ask operating system to create temp file
            translationTempFile = File.createTempFile("translations", null);
            // transfer MultipartFile to File
            multipartFile.transferTo(translationTempFile);
            // Save Translations
            translationService.save(translationTempFile);

            return responseHelper.createAndSendResponse(
                    TranslationsResponseCodeEnum.TRANSLATION_FILE_UPLOAD_SUCCESS, HttpStatus.OK,
                    messageSourceResolver.resolver("translation_file_upload_success",
                            localeResolver.resolveLocale(request)));

        } catch (final IOException ex) {
            throw new UploadTranslationFileException(ex.getMessage(), ex);
        } finally {
            if (translationTempFile != null) {
                translationTempFile.delete();
            }
        }

    }

}
