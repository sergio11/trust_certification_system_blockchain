package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedBcEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDTO;
import org.mapstruct.IterableMapping;
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
    public abstract CertificateIssuedDTO certificateIssuedEntityToCertificateIssued(CertificateIssuedBcEntity certificateIssuedEntity);

    /**
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "certificateIssuedEntityToCertificateIssued")
    public abstract Iterable<CertificateIssuedDTO> certificateIssuedEntityToCertificateIssued(Iterable<CertificateIssuedBcEntity> entity);
}
