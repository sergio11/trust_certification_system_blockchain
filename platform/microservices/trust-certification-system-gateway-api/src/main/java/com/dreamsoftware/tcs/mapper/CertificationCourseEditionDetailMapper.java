package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseEditionDetailDTO;
import org.mapstruct.*;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CertificationCourseEditionDetailMapper {

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(entity.getId().toString())", target = "id")
    })
    @Named("entityToDTO")
    public abstract CertificationCourseEditionDetailDTO entityToDTO(CertificationCourseEditionEntity entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<CertificationCourseEditionDetailDTO> entityToDTO(Iterable<CertificationCourseEditionEntity> entity);
}
