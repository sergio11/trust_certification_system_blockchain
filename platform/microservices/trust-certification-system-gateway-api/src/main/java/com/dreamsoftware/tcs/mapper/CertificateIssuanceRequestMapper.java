package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuanceRequestDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificateIssuanceRequestMapper {

    @Autowired
    protected SimpleCertificationCourseDetailMapper certificationCourseDetailMapper;

    @Mappings({
        @Mapping(expression = "java(entity.getId().toString())", target = "id"),
        @Mapping(expression = "java(entity.getStatus().name())", target = "status"),
        @Mapping(expression = "java(entity.getStudent().getId().toString())", target = "studentWalletHash"),
        @Mapping(expression = "java(entity.getCa().getId().toString())", target = "caWalletHash")
    })
    @Named("entityToDTO")
    public abstract CertificateIssuanceRequestDTO entityToDTO(CertificateIssuanceRequestEntity entity);

    /**
     * @param entity list
     * @return dto list
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<CertificateIssuanceRequestDTO> entityToDTO(Iterable<CertificateIssuanceRequestEntity> entity);
}
