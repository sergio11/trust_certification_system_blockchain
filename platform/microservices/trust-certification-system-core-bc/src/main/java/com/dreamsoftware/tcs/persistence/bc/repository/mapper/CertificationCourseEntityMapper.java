package com.dreamsoftware.tcs.persistence.bc.repository.mapper;

import com.dreamsoftware.tcs.contracts.CertificationCourseContract.CertificationCourseRecord;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseBcEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationCourseEntityMapper {

    /**
     * Certification Course Record To Certification Course Entity
     *
     * @param caRecord
     * @return
     */
    @Mappings({})
    @Named("courseRecordToCertificationCourseEntity")
    public abstract CertificationCourseBcEntity courseRecordToCertificationCourseEntity(CertificationCourseRecord caRecord);

    /**
     *
     * @param caRecord
     * @return
     */
    @IterableMapping(qualifiedByName = "courseRecordToCertificationCourseEntity")
    public abstract Iterable<CertificationCourseBcEntity> courseRecordToCertificationCourseEntity(Iterable<CertificationCourseRecord> caRecord);
}
