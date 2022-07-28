package com.dreamsoftware.tcs.persistence.bc.repository.mapper;

import com.dreamsoftware.tcs.contracts.CertificationCourseContract.CertificationCourseRecord;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
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
    public abstract CertificationCourseModelEntity courseRecordToCertificationCourseEntity(CertificationCourseRecord caRecord);

}
