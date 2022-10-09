package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import org.mapstruct.*;

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
