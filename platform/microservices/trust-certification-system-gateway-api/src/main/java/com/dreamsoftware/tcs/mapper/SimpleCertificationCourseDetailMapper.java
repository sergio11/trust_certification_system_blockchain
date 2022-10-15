package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseBcEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationCourseDetailDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SimpleCertificationCourseDetailMapper {

    @Autowired
    protected SimpleCertificationAuthorityDetailMapper simpleCertificationAuthorityDetailMapper;

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(entity.getLeft().getId().toString())", target = "id"),
            @Mapping(expression = "java(entity.getLeft().getName())", target = "name"),
            @Mapping(expression = "java(entity.getLeft().getDescription())", target = "description"),
            @Mapping(expression = "java(entity.getLeft().getStatus().name())", target = "status"),
            @Mapping(expression = "java(entity.getLeft().getCreatedAt())", target = "createdAt"),
            @Mapping(expression = "java(simpleCertificationAuthorityDetailMapper.entityToDTO(entity.getLeft().getCa()))", target = "ca"),
            @Mapping(expression = "java(entity.getRight().getCostOfIssuingCertificate())", target = "costOfIssuingCertificate"),
            @Mapping(expression = "java(entity.getRight().getCostOfRenewingCertificate())", target = "costOfRenewingCertificate"),
            @Mapping(expression = "java(entity.getRight().getDurationInHours())", target = "durationInHours"),
            @Mapping(expression = "java(entity.getRight().getExpirationInDays())", target = "expirationInDays"),
            @Mapping(expression = "java(entity.getRight().getCanBeRenewed())", target = "canBeRenewed"),
    })
    @Named("entityToDTO")
    public abstract SimpleCertificationCourseDetailDTO entityToDTO(Pair<CertificationCourseEntity, CertificationCourseBcEntity> entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<SimpleCertificationCourseDetailDTO> entityListToDTOList(Iterable<Pair<CertificationCourseEntity, CertificationCourseBcEntity>> entity);
}
