package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.ImageDataEntity;
import com.dreamsoftware.tcs.web.dto.response.UserPhotoDTO;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * User Photo Mapper
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserPhotoMapper {

    /**
     * User Photo Entity to User Photo DTO mapping
     *
     * @param entity
     * @return DTO
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract UserPhotoDTO entityToDTO(ImageDataEntity entity);

    /**
     * User Photo Entity list to User Photo DTO list
     *
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract List<UserPhotoDTO> entityToDTO(List<ImageDataEntity> entity);

}
