package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserLoginEntity;
import com.dreamsoftware.tcs.web.dto.response.UserLoginDTO;
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
public abstract class UserLoginMapper {

    /**
     * @param entity
     * @return DTO
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity")
    })
    @Named("entityToDTO")
    public abstract UserLoginDTO entityToDTO(UserLoginEntity entity);

    /**
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract List<UserLoginDTO> entityToDTO(List<UserLoginEntity> entity);

}
