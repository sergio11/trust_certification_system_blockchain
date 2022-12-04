package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseAttendeeControlEntity;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseAttendeeControlDTO;
import org.mapstruct.*;

/**
 * Save certification course attendee control Mapper
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SaveCertificationCourseAttendeeControlMapper {


    /**
     *
     * @param dto
     * @return
     */
    @Mappings({})
    @Named("dtoToEntity")
    public abstract CertificationCourseAttendeeControlEntity dtoToEntity(SaveCertificationCourseAttendeeControlDTO dto);

    /**
     *
     * @param dtoList
     * @return
     */
    @IterableMapping(qualifiedByName = "dtoToEntity")
    public abstract Iterable<CertificationCourseAttendeeControlEntity> dtoToEntity(Iterable<SaveCertificationCourseAttendeeControlDTO> dtoList);

}
