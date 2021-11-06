package com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper;

import com.dreamsoftware.blockchaingateway.contracts.TrustCertificationContract;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificateIssuedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class TrustCertificationEntityMapper {

    /**
     * Certificate Record To Certificate Issued Entity
     *
     * @param certificateRecord
     * @return
     */
    @Mappings({})
    @Named("certificateRecordToCertificateIssuedEntity")
    public abstract CertificateIssuedEntity certificateRecordToCertificateIssuedEntity(TrustCertificationContract.CertificateRecord certificateRecord);

}
