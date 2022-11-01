package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseDTO;
import org.mapstruct.*;

/**
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UpdateCertificationCourseMapper {

    /**
     * @param entity
     * @return
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract UpdateCertificationCourseDTO entityToDTO(final CertificationCourseEntity entity);

    /**
     *
     * @param entity
     * @param model
     * @return
     */
    @Mappings({
        @Mapping(source = "model.costOfIssuingCertificate", target = "costOfIssuingCertificate"),
        @Mapping(source = "model.costOfRenewingCertificate", target = "costOfRenewingCertificate"),
        @Mapping(source = "model.description", target = "description"),
        @Mapping(source = "model.name", target = "name")
    })
    public abstract CertificationCourseEntity update(@MappingTarget CertificationCourseEntity entity, UpdateCertificationCourseDTO model);
}
