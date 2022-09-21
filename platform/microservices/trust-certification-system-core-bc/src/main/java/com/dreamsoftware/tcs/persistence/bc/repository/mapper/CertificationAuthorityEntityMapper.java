package com.dreamsoftware.tcs.persistence.bc.repository.mapper;

import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract.CertificationAuthorityRecord;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationAuthorityEntityMapper {

    /**
     * Certification Authority Record To Certification Authority Entity
     *
     * @param caRecord
     * @return
     */
    @Mappings({})
    @Named("caRecordToCaEntity")
    public abstract CertificationAuthorityEntity caRecordToCaEntity(CertificationAuthorityRecord caRecord);

    /**
     *
     * @param caRecord
     * @return
     */
    @IterableMapping(qualifiedByName = "caRecordToCaEntity")
    public abstract Iterable<CertificationAuthorityEntity> caRecordToCaEntity(Iterable<CertificationAuthorityRecord> caRecord);

}
