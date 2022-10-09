package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationAuthorityDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SimpleCertificationAuthorityDetailMapper {

    /**
     * Certification Authority Entity to Certification Authority Detail
     *
     * @param caEntity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(caEntity.getId().toString())", target = "id")
    })
    @Named("caEntityToCaDetail")
    public abstract SimpleCertificationAuthorityDetailDTO entityToDTO(CertificationAuthorityEntity caEntity);
}
