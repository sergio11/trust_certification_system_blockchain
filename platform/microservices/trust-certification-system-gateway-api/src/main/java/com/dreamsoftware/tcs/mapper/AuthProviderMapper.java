package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderEntity;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationProviderDTO;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * Simple Auth Provider Mapper
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AuthProviderMapper {

    /**
     * Auth Provider Entity to Auth Provider DTO mapping
     *
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
        @Mapping(expression = "java(entity.getType().name())", target = "type")
    })
    @Named("entityToDTO")
    public abstract AuthenticationProviderDTO entityToDTO(final AuthenticationProviderEntity entity);

    /**
     * Auth Provider Entity list to Auth Provider DTO list
     *
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract List<AuthenticationProviderDTO> entityToDTO(List<AuthenticationProviderEntity> entity);
}
