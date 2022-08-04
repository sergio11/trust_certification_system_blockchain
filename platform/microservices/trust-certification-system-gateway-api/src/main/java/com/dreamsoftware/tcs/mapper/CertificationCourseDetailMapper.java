package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationCourseDetailMapper {

    /**
     * @param entity
     * @return
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract CertificationCourseDetailDTO entityToDTO(CertificationCourseModelEntity entity);

    /**
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "id")
    })
    @Named("entityToDTO")
    public abstract CertificationCourseDetailDTO entityToDTO(CertificationCourseEntity entity);
}
