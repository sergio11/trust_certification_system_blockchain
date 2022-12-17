package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import org.bson.types.ObjectId;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CertificationCourseDetailMapper {

    @Autowired
    protected SimpleCertificationAuthorityDetailMapper simpleCertificationAuthorityDetailMapper;

    @Autowired
    protected CertificationCourseEditionDetailMapper certificationCourseEditionDetailMapper;

    @Autowired
    protected CertificationCourseEditionRepository certificationCourseEditionRepository;

    /**
     *
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(entity.getId().toString())", target = "id"),
            @Mapping(expression = "java(entity.getStatus().name())", target = "status"),
            @Mapping(expression = "java(simpleCertificationAuthorityDetailMapper.entityToDTO(entity.getCa()))", target = "ca"),
            @Mapping(expression = "java(certificationCourseEditionDetailMapper.entityToDTO(getCertificationCourseEditions(entity.getId())))", target = "editions")
    })
    @Named("entityToDTO")
    public abstract CertificationCourseDetailDTO entityToDTO(CertificationCourseEntity entity);

    /**
     *
     * @param entity
     * @return
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    public abstract Iterable<CertificationCourseDetailDTO> entityListToDTOList(Iterable<CertificationCourseEntity> entity);

    /**
     *
     * @param courseId
     * @return
     */
    protected Iterable<CertificationCourseEditionEntity> getCertificationCourseEditions(final ObjectId courseId) {
        return certificationCourseEditionRepository.findAllByCourse(courseId);
    }
}
