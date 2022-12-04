package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseAttendeeControlEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseAttendeeControlDetailDTO;
import org.mapstruct.*;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CertificationCourseAttendeeControlDetailMapper {

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract CertificationCourseAttendeeControlDetailDTO entityToDTO(CertificationCourseAttendeeControlEntity entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<CertificationCourseAttendeeControlDetailDTO> entityToDTO(Iterable<CertificationCourseAttendeeControlEntity> entity);
}
