package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleAuthenticationProviderDTO;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * Simple Auth Provider Mapper
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SimpleAuthProviderMapper {

    /**
     * Auth Provider Entity to Simple Auth Provider DTO mapping
     *
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
        @Mapping(expression = "java(entity.getType().name())", target = "type")
    })
    @Named("entityToDTO")
    public abstract SimpleAuthenticationProviderDTO entityToDTO(final AuthenticationProviderEntity entity);

    /**
     * Auth Provider Entity list to Simple Auth Provider DTO list
     *
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract List<SimpleAuthenticationProviderDTO> entityToDTO(List<AuthenticationProviderEntity> entity);

}
