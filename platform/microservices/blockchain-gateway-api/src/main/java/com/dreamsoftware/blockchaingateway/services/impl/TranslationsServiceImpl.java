package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.mapper.SaveTranslationMapper;
import com.dreamsoftware.blockchaingateway.mapper.TranslationMapper;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TranslationEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TranslationLanguageEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.TranslationRepository;
import com.dreamsoftware.blockchaingateway.services.ITranslationsService;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveTranslationDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.TranslationDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.thymeleaf.util.StringUtils;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class TranslationsServiceImpl implements ITranslationsService {

    private static final Logger logger = LoggerFactory.getLogger(TranslationsServiceImpl.class);

    @Value("classpath:/i18n/translation_default.xlsx")
    private Resource translationDefaultFile;

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

                    if (!columnIdxMap.containsKey(TranslationXlsFieldNamesEnum.NAME.name())
                            || !columnIdxMap.containsKey(TranslationXlsFieldNamesEnum.TARGET.name())) {
                        continue;
                    }

                    final Cell nameCell = row.getCell(columnIdxMap.get(TranslationXlsFieldNamesEnum.NAME.name()));
                    if (nameCell.getCellType() != CellType.STRING) {
                        continue;
                    }

                    final String translationName = nameCell.getStringCellValue();

                    final Cell targetCell = row.getCell(columnIdxMap.get(TranslationXlsFieldNamesEnum.TARGET.name()));
                    if (targetCell.getCellType() != CellType.STRING) {
                        continue;
                    }

                    final String translationTarget = targetCell.getStringCellValue();

                    for (final TranslationXlsFieldNamesEnum field : TranslationXlsFieldNamesEnum.values()) {
                        if (field != TranslationXlsFieldNamesEnum.NAME
                                && field != TranslationXlsFieldNamesEnum.TARGET) {

                            final Cell cell = row.getCell(columnIdxMap.get(field.name()));
                            if (cell != null) {
                                if (targetCell.getCellType() == CellType.STRING) {
                                    final String value = cell.getStringCellValue();
                                    if (!StringUtils.isEmptyOrWhitespace(value)) {
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
            }

            logger.debug("Translations to save -> " + saveTranslationDTOList.size());
            save(saveTranslationDTOList);

        } catch (final IOException | InvalidFormatException ex) {
            logger.debug("Process Translation Default File failed");
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException ex) {
                logger.debug("Process Translation Default File failed");
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
        logger.debug("TranslationEntity Id to save -> " + translationToSave.getId());
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

    @PostConstruct
    protected void onInit() {
        importDefaultTranslations();
    }

    /**
     * Import Default Translations
     */
    private void importDefaultTranslations() {
        try {
            save(translationDefaultFile.getFile());
        } catch (IOException ex) {
        }
    }

    private enum TranslationXlsFieldNamesEnum {
        TARGET, NAME, es_ES, en_GB, de_DE, fr_FR, pt_PT, it_IT;
    }
}
