package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceGroupEntity;
import com.dreamsoftware.tcs.web.dto.response.DeviceGroupDTO;
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
public abstract class DeviceGroupEntityMapper {

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "identity"),
        @Mapping(source = "entity.createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd"),
        @Mapping(expression = "java(entity.getOwner().getId().toString())", target = "owner")
    })
    @Named("entityToDTO")
    public abstract DeviceGroupDTO entityToDTO(final DeviceGroupEntity entity);

    /**
     *
     * @param entityList
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<DeviceGroupDTO> entityToDTO(final Iterable<DeviceGroupEntity> entityList);
}
