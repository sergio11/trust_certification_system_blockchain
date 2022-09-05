package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserLoginEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserLoginDTO;
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
public abstract class SimpleUserLoginMapper {

    /**
     * User Login Entity to User Login DTO
     *
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(entity.getPlatform().name())", target = "platform")
    })
    @Named("entityToDTO")
    public abstract SimpleUserLoginDTO entityToDTO(UserLoginEntity entity);

    /**
     * User Login Entity List to User Login DTO List
     *
     * @param entityList
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<SimpleUserLoginDTO> entityToDTOList(Iterable<UserLoginEntity> entityList);

}
