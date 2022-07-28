package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationCourseDetailMapper {

    /**
     * Certification Course Entity to Certification Course Detail
     *
     * @param certificationCourseEntity
     * @return
     */
    @Mappings({})
    @Named("certificationCourseEntityToCertificationCourseDetail")
    public abstract CertificationCourseDetailDTO certificationCourseEntityToCertificationCourseDetail(CertificationCourseEntity certificationCourseEntity);
}
