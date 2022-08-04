package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
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
    @Named("entityToDTO")
    public abstract CertificateIssuedDTO entityToDTO(CertificateIssuedEntity certificateIssuedEntity);

    /**
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<CertificateIssuedDTO> entityToDTO(Iterable<CertificateIssuedEntity> entity);
}
