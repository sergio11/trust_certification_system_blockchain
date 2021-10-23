package com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper;

import com.dreamsoftware.blockchaingateway.contracts.CertificationCourseContract.CertificationCourseRecord;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEntity;
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
    public abstract CertificationCourseEntity courseRecordToCertificationCourseEntity(CertificationCourseRecord caRecord);

}
