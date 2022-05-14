package com.dreamsoftware.blockchaingateway.mapper;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificateIssuedDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificateIssuedMapper {

    @Mappings({})
    @Named("certificateIssuedEntityToCertificateIssued")
    public abstract CertificateIssuedDTO certificateIssuedEntityToCertificateIssued(CertificateIssuedEntity certificateIssuedEntity);
}
