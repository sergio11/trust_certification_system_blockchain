package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificationAuthorityDetailMapper {

    /**
     * Certification Authority Entity to Certification Authority Detail
     *
     * @param caEntity
     * @param userEntity
     * @return
     */
    @Mappings({
        @Mapping(expression = "java(userEntity.getId().toString())", target = "id"),
        @Mapping(expression = "java(userEntity.getName())", target = "name"),
        @Mapping(expression = "java(userEntity.getState().name())", target = "state"),
        @Mapping(expression = "java(userEntity.getType().name())", target = "type"),
        @Mapping(expression = "java(userEntity.getEmail())", target = "email"),
        @Mapping(expression = "java(caEntity.getDefaultCostOfIssuingCertificate().intValue())", target = "defaultCostOfIssuingCertificate"),
        @Mapping(expression = "java(caEntity.getIsEnabled())", target = "isEnabled")
    })
    @Named("caEntityToCaDetail")
    public abstract CertificationAuthorityDetailDTO entityToDTO(CertificationAuthorityEntity caEntity, UserEntity userEntity);
}
