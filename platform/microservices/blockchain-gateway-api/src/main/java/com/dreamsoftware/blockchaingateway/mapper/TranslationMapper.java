package com.dreamsoftware.blockchaingateway.mapper;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TranslationEntity;
import com.dreamsoftware.blockchaingateway.web.dto.response.TranslationDTO;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class TranslationMapper {

    /**
     * Translation Entity to Translation DTO mapping
     *
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
        @Mapping(expression = "java(entity.getLanguage().name())", target = "language")
    })
    @Named("entityToDTO")
    public abstract TranslationDTO entityToDTO(final TranslationEntity entity);

    /**
     * Translation Entity list to Translation DTO list
     *
     * @param entityList
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<TranslationDTO> entityToDTO(Iterable<TranslationEntity> entityList);

    /**
     * Translation Entity list to Translation DTO list
     *
     * @param entityList
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<TranslationDTO> entityToDTO(List<TranslationEntity> entityList);
}
