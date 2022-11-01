package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseEditionDTO;
import org.mapstruct.*;

/**
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UpdateCertificationCourseEditionMapper {

    /**
     * @param entity
     * @return
     */
    @Mappings({})
    @Named("entityToDTO")
    public abstract UpdateCertificationCourseEditionDTO entityToDTO(final CertificationCourseEditionEntity entity);

    /**
     * @param entity
     * @param model
     * @return
     */
    @Mappings({
            @Mapping(source = "model.costOfIssuingCertificate", target = "costOfIssuingCertificate"),
            @Mapping(source = "model.costOfRenewingCertificate", target = "costOfRenewingCertificate"),
            @Mapping(source = "model.durationInHours", target = "durationInHours"),
            @Mapping(source = "model.expirationInDays", target = "expirationInDays"),
            @Mapping(source = "model.canBeRenewed", target = "canBeRenewed"),
            @Mapping(source = "model.name", target = "name")
    })
    public abstract CertificationCourseEditionEntity update(@MappingTarget CertificationCourseEditionEntity entity, UpdateCertificationCourseEditionDTO model);
}
