package com.dreamsoftware.tcs.mapper;

import com.dreamsoftware.tcs.persistence.nosql.entity.*;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.web.controller.course.error.exception.CertificationCourseNotFoundException;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseEditionDTO;
import org.bson.types.ObjectId;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Save Certification Course Edition Mapper
 *
 * @author ssanchez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class SaveCertificationCourseEditionMapper {

    @Autowired
    protected CertificationCourseRepository certificationCourseRepository;

    @Autowired
    protected UserRepository userRepository;

    /**
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(expression = "java(getCertificationCourse(dto.getCertificationCourseId()))", target = "course"),
            @Mapping(expression = "java(getCertificationCourseEditionInitialState())", target = "status"),
            @Mapping(expression = "java(getCaMember(dto.getCaWalletHash()))", target = "caMember"),
            @Mapping(expression = "java(mapToAttendeeControl(dto))", target = "attendeeControl")
    })
    @Named("dtoToEntity")
    public abstract CertificationCourseEditionEntity dtoToEntity(SaveCertificationCourseEditionDTO dto);

    /**
     * @param dtoList
     * @return
     */
    @IterableMapping(qualifiedByName = "dtoToEntity")
    public abstract Iterable<CertificationCourseEditionEntity> dtoToEntity(Iterable<SaveCertificationCourseEditionDTO> dtoList);

    /**
     * Get Certification Course
     *
     * @param courseId
     * @return
     */
    protected CertificationCourseEntity getCertificationCourse(final String courseId) {
        return certificationCourseRepository.findById(new ObjectId(courseId))
                .orElseThrow(CertificationCourseNotFoundException::new);
    }

    /**
     * Get Certification Course Edition Initial State
     *
     * @return
     */
    protected CertificationCourseStateEnum getCertificationCourseEditionInitialState() {
        return CertificationCourseStateEnum.NOT_VALIDATED;
    }

    /**
     * @param caMemberWalletHash
     * @return
     */
    protected UserEntity getCaMember(final String caMemberWalletHash) {
        return userRepository.findOneByWalletHash(caMemberWalletHash)
                .orElseThrow(IllegalStateException::new);
    }


    protected CertificationCourseAttendeeControlEntity mapToAttendeeControl(final SaveCertificationCourseEditionDTO dto) {
        return dto.getMaxAttendanceCount() != null &&
                dto.getMaxAttendeeCount() != null &&
                dto.getMinPercentageAttendanceRequired() != null ? CertificationCourseAttendeeControlEntity.builder()
                .enrollCost(dto.getEnrollCost())
                .maxAttendeeCount(dto.getMaxAttendeeCount())
                .maxAttendanceCount(dto.getMaxAttendanceCount())
                .minPercentageAttendanceRequired(dto.getMinPercentageAttendanceRequired())
                .build() : null;
    }
}
