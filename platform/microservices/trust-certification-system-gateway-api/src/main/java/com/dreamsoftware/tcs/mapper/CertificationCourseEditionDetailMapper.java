package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseEditionDetailDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CertificationCourseEditionDetailMapper {

    @Autowired
    protected CertificationCourseAttendeeControlDetailMapper certificationCourseAttendeeControlDetailMapper;

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(entity.getId().toString())", target = "id"),
            @Mapping(expression = "java(entity.getAttendeeControl() != null ? certificationCourseAttendeeControlDetailMapper.entityToDTO(entity.getAttendeeControl()) : null)", target = "attendeeControlDetail")
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
