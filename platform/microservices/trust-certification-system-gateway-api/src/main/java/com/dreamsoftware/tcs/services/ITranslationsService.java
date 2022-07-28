package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.SaveTranslationDTO;
import com.dreamsoftware.tcs.web.dto.response.TranslationDTO;
import java.io.File;
import java.util.List;

/**
 *
 * @author ssanchez
 */
public interface ITranslationsService {

    /**
     *
     * @param language
     * @return
     */
    Iterable<TranslationDTO> findByLanguageOrderByNameAsc(final String language);

    /**
     *
     * @param translationFileInXlsformat
     */
    void save(final File translationFileInXlsformat);

    /**
     * Save Model
     *
     * @param model
     * @return
     */
    TranslationDTO save(final SaveTranslationDTO model);

    /**
     * Save Model List
     *
     * @param modelList
     * @return
     */
    Iterable<TranslationDTO> save(final List<SaveTranslationDTO> modelList);
}
