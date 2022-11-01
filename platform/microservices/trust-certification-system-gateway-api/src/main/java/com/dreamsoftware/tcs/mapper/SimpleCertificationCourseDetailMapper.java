package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationCourseDetailDTO;
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
            @Mapping(expression = "java(entity.getId().toString())", target = "id"),
            @Mapping(expression = "java(entity.getStatus().name())", target = "status"),
            @Mapping(expression = "java(simpleCertificationAuthorityDetailMapper.entityToDTO(entity.getCa()))", target = "ca")
    })
    @Named("entityToDTO")
    public abstract SimpleCertificationCourseDetailDTO entityToDTO(CertificationCourseEntity entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<SimpleCertificationCourseDetailDTO> entityListToDTOList(Iterable<CertificationCourseEntity> entity);
}
