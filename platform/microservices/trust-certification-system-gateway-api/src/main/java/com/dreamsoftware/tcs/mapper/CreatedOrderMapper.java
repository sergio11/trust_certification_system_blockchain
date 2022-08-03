package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.web.dto.response.OrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CreatedOrderMapper {

    /**
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "id"),
        @Mapping(expression = "java(entity.getStatus().name())", target = "status"),
        @Mapping(expression = "java(entity.getUser().getId().toString())", target = "userId")
    })
    @Named("entityToDTO")
    public abstract OrderDetailDTO entityToDTO(CreatedOrderEntity entity);
}
