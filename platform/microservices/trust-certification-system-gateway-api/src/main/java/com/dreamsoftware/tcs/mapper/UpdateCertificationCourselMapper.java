package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseDTO;
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
public abstract class UpdateCertificationCourselMapper {

    /**
     * @param entity
     * @return
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract UpdateCertificationCourseDTO entityToDTO(final CertificationCourseModelEntity entity);

    /**
     *
     * @param entity
     * @param model
     * @return
     */
    @Mappings({
        @Mapping(source = "model.name", target = "name"),
        @Mapping(source = "model.costOfIssuingCertificate", target = "costOfIssuingCertificate"),
        @Mapping(source = "model.costOfRenewingCertificate", target = "costOfRenewingCertificate"),
        @Mapping(source = "model.durationInHours", target = "durationInHours"),
        @Mapping(source = "model.expirationInDays", target = "expirationInDays"),
        @Mapping(source = "model.canBeRenewed", target = "canBeRenewed")
    })
    public abstract CertificationCourseModelEntity update(@MappingTarget CertificationCourseModelEntity entity, UpdateCertificationCourseDTO model);
}
