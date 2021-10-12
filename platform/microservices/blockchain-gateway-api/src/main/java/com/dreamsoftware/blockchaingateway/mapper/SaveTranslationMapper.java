package com.dreamsoftware.blockchaingateway.mapper;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TranslationEntity;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveTranslationDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * Save Translation Mapper
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SaveTranslationMapper {

    /**
     * Save Translation DTO to Translation Entity
     *
     * @param dto
     * @return
     */
    @Mappings({
        @Mapping(expression = "java( com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TranslationLanguageEnum.valueOf(dto.getLanguage()))", target = "language")
    })
    @Named("saveTranslationDTOToTranslationEntity")
    public abstract TranslationEntity saveTranslationDTOToTranslationEntity(SaveTranslationDTO dto);

    /**
     * Save Translation DTO to Translation Entity
     *
     * @param dtoList
     * @return
     */
    @IterableMapping(qualifiedByName = "saveTranslationDTOToTranslationEntity")
    public abstract Iterable<TranslationEntity> saveTranslationDTOToTranslationEntity(Iterable<SaveTranslationDTO> dtoList);
}
