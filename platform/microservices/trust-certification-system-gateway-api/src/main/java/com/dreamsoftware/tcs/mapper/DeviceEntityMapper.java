package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceGroupEntity;
import com.dreamsoftware.tcs.web.dto.response.DeviceDTO;
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
public abstract class DeviceEntityMapper {

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(source = "entity.createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "entity.deviceGroup.notificationKeyName", target = "notificationKeyName"),
        @Mapping(source = "entity.deviceGroup.notificationKey", target = "notificationKey"),
        @Mapping(expression = "java(parseOwnerId(entity))", target = "owner")
    })
    @Named("entityToDTO")
    public abstract DeviceDTO entityToDTO(DeviceEntity entity);

    /**
     *
     * @param entityList
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<DeviceDTO> entityToDTO(Iterable<DeviceEntity> entityList);

    /**
     *
     * @param deviceEntity
     * @return
     */
    protected String parseOwnerId(DeviceEntity deviceEntity) {
        String ownerId = null;
        if (deviceEntity.getDeviceGroup() != null) {
            final DeviceGroupEntity deviceGroup = deviceEntity.getDeviceGroup();
            if (deviceGroup.getOwner() != null) {
                ownerId = deviceGroup.getOwner().getId().toString();
            }
        }
        return ownerId;
    }
}
