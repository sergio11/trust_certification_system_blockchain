package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UpdateCertificationAuthorityMapper {

    /**
     * @param entity
     * @return
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract UpdateCertificationAuthorityDTO entityToDTO(final CertificationAuthorityEntity entity);

    /**
     *
     * @param entity
     * @param model
     * @return
     */
    @Mappings({
        @Mapping(source = "model.name", target = "name"),
        @Mapping(source = "model.defaultCostOfIssuingCertificate", target = "defaultCostOfIssuingCertificate")
    })
    public abstract CertificationAuthorityEntity update(@MappingTarget CertificationAuthorityEntity entity, UpdateCertificationAuthorityDTO model);
}
