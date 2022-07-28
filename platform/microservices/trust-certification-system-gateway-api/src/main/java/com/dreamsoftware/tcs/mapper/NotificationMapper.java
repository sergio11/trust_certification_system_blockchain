package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationEntity;
import com.dreamsoftware.tcs.web.dto.response.NotificationDTO;
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
public abstract class NotificationMapper {

    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "id"),
        @Mapping(expression = "java(entity.getLevel().name())", target = "level")
    })
    @Named("notificationEntityToNotificationDTO")
    public abstract NotificationDTO entityToDTO(NotificationEntity entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "notificationEntityToNotificationDTO")
    public abstract Iterable<NotificationDTO> entityToDTO(Iterable<NotificationEntity> entity);
}
