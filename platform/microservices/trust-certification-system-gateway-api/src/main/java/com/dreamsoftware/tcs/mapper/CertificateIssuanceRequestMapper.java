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
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CertificateIssuanceRequestMapper {

    @Autowired
    protected CertificationCourseEditionDetailMapper certificationCourseEditionDetailMapper;

    @Autowired
    protected SimpleUserMapper simpleUserMapper;

    @Mappings({
            @Mapping(expression = "java(entity.getId().toString())", target = "id"),
            @Mapping(expression = "java(entity.getStatus().name())", target = "status"),
            @Mapping(expression = "java(simpleUserMapper.entityToDTO(entity.getStudent()))", target = "student"),
            @Mapping(expression = "java(simpleUserMapper.entityToDTO(entity.getCaMember()))", target = "caMember"),
            @Mapping(expression = "java(certificationCourseEditionDetailMapper.entityToDTO(entity.getCourse()))", target = "course")
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
