package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
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
public abstract class SimpleUserMapper {

    /**
     * User Entity to Simple User DTO mapping
     *
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
        @Mapping(expression = "java(entity.getType().name())", target = "type"),
        @Mapping(expression = "java(entity.getState().name())", target = "state")
    })
    @Named("userEntityToSimpleUserDTO")
    public abstract SimpleUserDTO entityToDTO(UserEntity entity);

    /**
     * User Entity list to Simple User DTO list
     *
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "userEntityToSimpleUserDTO")
    public abstract Iterable<SimpleUserDTO> entityToDTO(Iterable<UserEntity> entity);
}
