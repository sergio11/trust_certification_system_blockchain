package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.mapper.SaveTranslationMapper;
import com.dreamsoftware.tcs.mapper.TranslationMapper;
import com.dreamsoftware.tcs.persistence.nosql.entity.TranslationEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.TranslationLanguageEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.TranslationRepository;
import com.dreamsoftware.tcs.services.ITranslationsService;
import com.dreamsoftware.tcs.web.dto.request.SaveTranslationDTO;
import com.dreamsoftware.tcs.web.dto.response.TranslationDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TranslationsServiceImpl implements ITranslationsService {

    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;
    private final SaveTranslationMapper saveTranslationMapper;

    /**
     *
     * @param language
     * @return
     */
    @Override
    public Iterable<TranslationDTO> findByLanguageOrderByNameAsc(String language) {
        Assert.notNull(language, "Language can not be null");
        return translationMapper.entityToDTO(
                translationRepository.findByLanguageOrderByNameAsc(
                        TranslationLanguageEnum.valueOf(language)));
    }

    /**
     *
     * @param translationFileInXlsformat
     */
    @Override
    public void save(File translationFileInXlsformat) {
        Assert.notNull(translationFileInXlsformat, "Translation File can not be null");
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(translationFileInXlsformat);
            final Sheet sheet = workbook.getSheetAt(0);
            final Map<String, Integer> columnIdxMap = new HashMap<>();
            final List<SaveTranslationDTO> saveTranslationDTOList = new ArrayList<>();

            translationRepository.deleteAll();

            for (final Row row : sheet) {
                if (row.getRowNum() == 0) {
                    //Process Header
                    for (final Cell cell : row) {
                        final CellType cellType = cell.getCellType();
                        if (cellType == CellType.STRING) {
                            final String cellText = cell.getStringCellValue();
                            try {
                                final TranslationXlsFieldNamesEnum field = TranslationXlsFieldNamesEnum.valueOf(cellText);
                                columnIdxMap.put(field.name(), cell.getColumnIndex());
                            } catch (final Exception ex) {
                            }
                        }
                    }
                } else {

                    if (!columnIdxMap.containsKey(TranslationXlsFieldNamesEnum.NAME.name())) {
                        continue;
                    }

                    final Cell nameCell = row.getCell(columnIdxMap.get(TranslationXlsFieldNamesEnum.NAME.name()));
                    if (nameCell.getCellType() != CellType.STRING) {
                        continue;
                    }

                    final String translationName = nameCell.getStringCellValue();

                    for (final TranslationXlsFieldNamesEnum field : TranslationXlsFieldNamesEnum.values()) {
                        if (field != TranslationXlsFieldNamesEnum.NAME) {

                            final Cell cell = row.getCell(columnIdxMap.get(field.name()));
                            if (cell != null) {
                                final String value = cell.getStringCellValue();
                                if (!StringUtils.isBlank(value)) {
                                    saveTranslationDTOList.add(SaveTranslationDTO.builder()
                                            .name(translationName)
                                            .language(field.name())
                                            .value(value)
                                            .build());
                                }
                            }

                        }
                    }

                }
            }

            log.debug("Translations to save -> " + saveTranslationDTOList.size());
            save(saveTranslationDTOList);

        } catch (final IOException | InvalidFormatException ex) {
            log.debug("Process Translation Default File failed");
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException ex) {
                log.debug("Process Translation Default File failed");
            }
        }
    }

    /**
     *
     * @param model
     * @return
     */
    @Override
    public TranslationDTO save(SaveTranslationDTO model) {
        Assert.notNull(model, "Model can not be null");
        final TranslationEntity translationToSave = saveTranslationMapper.saveTranslationDTOToTranslationEntity(model);
        log.debug("TranslationEntity Id to save -> " + translationToSave.getId());
        final TranslationEntity translationSaved = translationRepository.save(translationToSave);
        return translationMapper.entityToDTO(translationSaved);
    }

    /**
     *
     * @param modelList
     * @return
     */
    @Override
    public Iterable<TranslationDTO> save(List<SaveTranslationDTO> modelList) {
        Assert.notNull(modelList, "Language can not be null");

        final List<TranslationEntity> translationEntityList = translationRepository.saveAll(
                saveTranslationMapper.saveTranslationDTOToTranslationEntity(modelList)
        );
        return translationMapper.entityToDTO(translationEntityList);
    }

    private enum TranslationXlsFieldNamesEnum {
        NAME, es_ES, en_GB, de_DE, fr_FR, pt_PT, it_IT;
    }
}
